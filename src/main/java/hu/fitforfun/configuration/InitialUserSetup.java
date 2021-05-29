package hu.fitforfun.configuration;

import hu.fitforfun.enums.Roles;
import hu.fitforfun.model.ContactData;
import hu.fitforfun.model.SportType;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.request.UserRegistrationModel;
import hu.fitforfun.model.user.Authority;
import hu.fitforfun.model.user.Role;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.InstructorService;
import hu.fitforfun.services.TrainingSessionService;
import hu.fitforfun.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class InitialUserSetup {

    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    InstructorService instructorService;
    @Autowired
    TrainingSessionService trainingSessionService;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    TrainingSessionRepository trainingSessionRepository;
    @Autowired
    TrainingSessionDetailsRepository trainingSessionDetailsRepository;
    @Autowired
    SportTypeRepository sportTypeRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {

     /*

        Authority readAuthority = createAuthority("READ_AUTHORITY");
        Authority writeAuthority = createAuthority("WRITE_AUTHORITY");
        Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

        Role roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        Role roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
        Role roleInstructor = createRole("ROLE_INSTRUCTOR", Arrays.asList(readAuthority, writeAuthority));
*/
/*        userService.createUser(createUser("Korponay", "Mihály", "pass",
                createContactData("admin@admin.hu", "702175709"),
                createAddress("Magyarország", "Pest", "Albertirsa", "Tó utca 26")), Roles.ROLE_ADMIN.name());


        userService.createUser(createUser("Korponay", "Mihály", "password",
                createContactData("korponay.mihaly@sonore.hu", "702175709"),
                createAddress("Magyarország", "Pest", "Albertirsa", "Tó utca 26")), Roles.ROLE_USER.name());*/


    }

    @Transactional
    private Authority createAuthority(String name) {
        Authority authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    private Role createRole(String name, List<Authority> authorities) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private ContactData createContactData(String email, String telNumber) {
        return new ContactData(email, telNumber);
    }

    @Transactional
    private Address createAddress(String country, String county, String city, String street) {
        Address address = new Address();
        address.setCountry(country);
        address.setCounty(county);
        address.setCity(cityRepository.findByCityNameIgnoreCase(city));
        address.setStreet(street);
        return address;
    }

    @Transactional
    private UserRegistrationModel createUser(String firstName, String lastName, String password, ContactData contactData, Address address) {

        UserRegistrationModel user = new UserRegistrationModel();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setShippingCountry(address.getCountry());
        user.setShippingCounty(address.getCounty());
        user.setShippingCity(address.getCity().getCityName());
        user.setShippingStreet(address.getStreet());
        user.setBillingCountry(address.getCountry());
        user.setBillingCounty(address.getCounty());
        user.setBillingCity(address.getCity().getCityName());
        user.setBillingStreet(address.getCounty());

        user.setTelNumber(contactData.getTelNumber());
        user.setEmail(contactData.getEmail());
        return user;
    }
}

