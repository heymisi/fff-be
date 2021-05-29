package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.request.UserRegistrationModel;
import hu.fitforfun.model.request.UserUpdateDuringTransactionRequestModel;
import hu.fitforfun.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getUserById(Long id) throws FitforfunException;

    boolean deleteUser(Long id, String password) throws FitforfunException;

    List<User> listUsers(int page, int limit);

    User createUser(UserRegistrationModel user, String role) throws Exception;

    User updateUser(Long id, User user) throws FitforfunException;

    User updateUserDuringTransaction(Long id, UserUpdateDuringTransactionRequestModel user) throws FitforfunException;

    boolean verifyEmailToken(String token);

    void requestPasswordReset(String email) throws Exception;

    boolean resetPassword(String token, String password);

    boolean changePassword(Long userId, String oldPassword, String newPassword) throws FitforfunException;

    List<User> listUsersByRole(String role);

}
