package hu.fitforfun.services.impl;

import hu.fitforfun.enums.Roles;
import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.ContactData;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.request.UserRegistrationModel;
import hu.fitforfun.model.request.UserUpdateDuringTransactionRequestModel;
import hu.fitforfun.model.shop.Cart;
import hu.fitforfun.model.user.PasswordResetTokenEntity;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.security.SecurityConstants;
import hu.fitforfun.security.UserPrincipal;
import hu.fitforfun.services.EmailService;
import hu.fitforfun.services.UserService;
import hu.fitforfun.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ContactDataRepository contactDataRepository;

    @Autowired
    private PasswordResetTokenEntityRepository passwordResetTokenEntityRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public User getUserById(Long id) throws FitforfunException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);

        return user.get();
    }


    @Override
    public boolean deleteUser(Long id, String password) throws FitforfunException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        if (!bCryptPasswordEncoder.matches(password, userToDelete.get().getPassword())) {
            return false;
        }

        List<TrainingSession> userTrainingSessions = trainingSessionRepository.findByClientIdIn(Collections.singletonList(id));

        if (userTrainingSessions != null) {
            userTrainingSessions.forEach(trainingSession -> {
                trainingSession.getClient().remove(userToDelete.get());
                trainingSessionRepository.save(trainingSession);
            });
        }

        Optional<Instructor> optionInstructor = instructorRepository.findByUser(userToDelete.get());
        if (optionInstructor.isPresent()) {
            instructorRepository.delete(optionInstructor.get());
            return true;
        }

        List<PasswordResetTokenEntity> passwordTokens = passwordResetTokenEntityRepository.findByUserDetails(userToDelete.get());
        if(passwordTokens.size() != 0){
            passwordTokens.forEach(psw->{
                passwordResetTokenEntityRepository.delete(psw);
            });
        }

        if(userToDelete.get().getTransactions() != null){
            userToDelete.get().getTransactions().forEach(transaction -> {
                transaction.setPurchaser(null);
                transactionRepository.save(transaction);
            });
        }
        userRepository.delete(userToDelete.get());
        return true;
    }

    @Override
    public List<User> listUsers(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<User> users = userRepository.findAll(pageableRequest);
        return users.getContent();
    }

    @Override
    public User createUser(UserRegistrationModel user, String role) throws Exception {
        User savedUser = new User();

        savedUser.setContactData(new ContactData());
        savedUser.setShippingAddress(new Address());
        savedUser.setBillingAddress(new Address());

        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        savedUser.getContactData().setTelNumber(user.getTelNumber());
        savedUser.getContactData().setEmail(user.getEmail());

        savedUser.getShippingAddress().setCountry(user.getShippingCountry());
        savedUser.getShippingAddress().setCounty(user.getShippingCounty());
        savedUser.getShippingAddress().setStreet(user.getShippingStreet());
        savedUser.getShippingAddress().setCity(cityRepository.findByCityNameIgnoreCase(user.getShippingCity()));

        savedUser.getBillingAddress().setCountry(user.getBillingCountry());
        savedUser.getBillingAddress().setCounty(user.getBillingCounty());
        savedUser.getBillingAddress().setStreet(user.getBillingStreet());
        savedUser.getBillingAddress().setCity(cityRepository.findByCityNameIgnoreCase(user.getBillingCity()));
        if(role.equals("ROLE_USER")){
            savedUser.setCart(new Cart());
        }
        savedUser.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName(role))));

        savedUser.setEmailVerificationToken(TokenUtils.generateToken(user.getEmail(), SecurityConstants.EXPIRATION_TIME));
        savedUser.setEmailVerificationStatus(false);
        emailService.sendRegistrationMail(savedUser);

        return userRepository.save(savedUser);
    }

    @Override
    public User updateUser(Long id, User user) throws FitforfunException {

        Optional<User> userToUpdate = userRepository.findById(id);
        if (!userToUpdate.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);

        if (!user.getFirstName().isEmpty() && !user.getFirstName().equals(userToUpdate.get().getFirstName()))
            userToUpdate.get().setFirstName(user.getFirstName());

        if (!user.getLastName().isEmpty() && !user.getLastName().equals(userToUpdate.get().getLastName()))
            userToUpdate.get().setLastName(user.getLastName());

        if (user.getBillingAddress() != null && user.getBillingAddress() != userToUpdate.get().getBillingAddress())
            userToUpdate.get().setBillingAddress(user.getBillingAddress());

        if (user.getShippingAddress() != null && user.getShippingAddress() != userToUpdate.get().getShippingAddress())
            userToUpdate.get().setShippingAddress(user.getShippingAddress());

        if (user.getContactData() != null && user.getContactData() != userToUpdate.get().getContactData())
            userToUpdate.get().setContactData(user.getContactData());

        return userRepository.save(userToUpdate.get());
    }

    @Override
    public User updateUserDuringTransaction(Long id, UserUpdateDuringTransactionRequestModel user) throws FitforfunException {
        Optional<User> userToUpdateOptional = userRepository.findById(id);
        if (!userToUpdateOptional.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        User userToUpdate = userToUpdateOptional.get();
        if (user.getFirstName() != null)
            userToUpdate.setFirstName(user.getFirstName());

        if (user.getLastName() != null)
            userToUpdate.setLastName(user.getLastName());

        if (user.getEmail() != null)
            userToUpdate.getContactData().setEmail(user.getEmail());

        if (user.getMobile() != null)
            userToUpdate.getContactData().setTelNumber(user.getMobile());

        if (user.getCity() != null) {
            userToUpdate.getShippingAddress().setCity(cityRepository.findByCityNameIgnoreCase(user.getCity()));
            userToUpdate.getBillingAddress().setCity(cityRepository.findByCityNameIgnoreCase(user.getCity()));
        }

        if (user.getStreet() != null) {
            userToUpdate.getShippingAddress().setStreet(user.getStreet());
            userToUpdate.getBillingAddress().setStreet(user.getStreet());
        }
        return userRepository.save(userToUpdate);
    }

    @Override
    public boolean verifyEmailToken(String token) {
        User user = userRepository.findUserByEmailVerificationToken(token);

        if (user != null) {
            if (!TokenUtils.hasTokenExpired(token)) {
                user.setEmailVerificationToken(null);
                user.setEmailVerificationStatus(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public void requestPasswordReset(String email) throws Exception {
        Optional<User> optionalUser = userRepository.findByContactDataEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = optionalUser.get();

        String token = TokenUtils.generateToken(user.getContactData().getEmail(), SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME);
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(user);
        passwordResetTokenEntityRepository.save(passwordResetTokenEntity);

        emailService.sendPasswordResetRequest(user, token);

    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;
        if (TokenUtils.hasTokenExpired(token)) {
            return false;
        }
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenEntityRepository.findByToken(token);
        if (passwordResetTokenEntity == null) {
            return false;
        }
        String encodedPass = bCryptPasswordEncoder.encode(password);
        User user = passwordResetTokenEntity.getUserDetails();
        user.setPassword(encodedPass);
        User savedUSer = userRepository.save(user);
        if (savedUSer.getPassword().equalsIgnoreCase(encodedPass)) {
            returnValue = true;
        }
        passwordResetTokenEntityRepository.delete(passwordResetTokenEntity);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByContactDataEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = optionalUser.get();
        return new UserPrincipal(user);
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        if (!bCryptPasswordEncoder.matches(oldPassword, optionalUser.get().getPassword())) {
            return false;
        }

        optionalUser.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(optionalUser.get());
        return true;
    }

    @Override
    public List<User> listUsersByRole(String role) {
        return userRepository.findByRolesIn(Arrays.asList(roleRepository.findByName(role)));
    }
}
