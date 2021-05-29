/*
package hu.fitforfun.configuration;

import hu.fitforfun.enums.TrainingSessionType;
import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.*;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.facility.FacilityPricing;
import hu.fitforfun.model.facility.OpeningHours;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.request.InstructorRegistrationModel;
import hu.fitforfun.model.request.UserRegistrationModel;
import hu.fitforfun.model.user.Authority;
import hu.fitforfun.model.user.Role;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.InstructorService;
import hu.fitforfun.services.SportFacilityService;
import hu.fitforfun.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class InitialSportFacilitySetup {

    @Autowired
    SportFacilityService service;

    @Autowired
    SportFacilityRepository sportFacilityRepository;

    @Autowired
    OpeningHoursRepository openingHoursRepository;

    @Autowired
    SportTypeRepository sportTypeRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstructorService instructorService;

    @Autowired
    TrainingSessionService trainingSessionService;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Autowired
    AddressRepository addressRepository;




    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {
        SportFacility sportFacility = new SportFacility();

        Address address = new Address();
        address.setCountry("Budapest");
        address.setCounty("Pest");
        address.setStreet("Stefánia Út 2.");
        address.setCity(cityRepository.findByCityNameIgnoreCase("Budapest"));
        sportFacility.setAddress(address);
        sportFacility.setName("Papp László Budapest Sportaréna");

        OpeningHours openingHours = new OpeningHours();
        openingHours.setDay(WeekDays.Hétfő);
        openingHours.setOpenTime(10D);
        openingHours.setCloseTime(20D);
        openingHours.setIsOpenNow(false);
        OpeningHours openingHours2 = new OpeningHours();
        openingHours2.setDay(WeekDays.Kedd);
        openingHours2.setOpenTime(12D);
        openingHours2.setCloseTime(22D);
        openingHours2.setIsOpenNow(false);
        OpeningHours openingHours3 = new OpeningHours();
        openingHours3.setDay(WeekDays.Szerda);
        openingHours3.setOpenTime(12D);
        openingHours3.setCloseTime(22D);
        openingHours3.setIsOpenNow(false);
        OpeningHours openingHours4 = new OpeningHours();
        openingHours4.setDay(WeekDays.Csütörtök);
        openingHours4.setOpenTime(7D);
        openingHours4.setCloseTime(20D);
        openingHours4.setIsOpenNow(false);
        OpeningHours openingHours5 = new OpeningHours();
        openingHours5.setDay(WeekDays.Péntek);
        openingHours5.setOpenTime(8D);
        openingHours5.setCloseTime(22D);
        openingHours5.setIsOpenNow(false);
        OpeningHours openingHours6 = new OpeningHours();
        openingHours6.setDay(WeekDays.Szombat);
        openingHours6.setOpenTime(9D);
        openingHours6.setCloseTime(22D);
        openingHours6.setIsOpenNow(false);
        OpeningHours openingHours7 = new OpeningHours();
        openingHours7.setDay(WeekDays.Vasárnap);
        openingHours7.setOpenTime(12D);
        openingHours7.setCloseTime(22D);
        openingHours7.setIsOpenNow(false);

        ContactData contactData = new ContactData();
        contactData.setTelNumber("702175709");
        contactData.setEmail("SportCsarnok@gmail.com");

        FacilityPricing facilityPricing = new FacilityPricing();
        facilityPricing.setAgeGroup("Felnőtt");
        facilityPricing.setSessionTicketPrice(15000D);
        facilityPricing.setSingleTicketPrice(1000D);

        FacilityPricing facilityPricing2 = new FacilityPricing();
        facilityPricing2.setAgeGroup("Diák");
        facilityPricing2.setSessionTicketPrice(10000D);
        facilityPricing2.setSingleTicketPrice(700D);

        FacilityPricing facilityPricing3 = new FacilityPricing();
        facilityPricing3.setAgeGroup("Nyugdíjas");
        facilityPricing3.setSessionTicketPrice(0D);
        facilityPricing3.setSingleTicketPrice(0D);

        sportFacility.setOpeningHours(Arrays.asList(openingHours, openingHours2, openingHours3, openingHours4, openingHours5, openingHours6, openingHours7));
        sportFacility.setDescription("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque,\n" +
                "              nulla? Quos cum ex quis soluta, a laboriosam. Dicta expedita corporis animi vero voluptate voluptatibus\n" +
                "              possimus, veniam magni quis!");
        sportFacility.setContactData(contactData);
        sportFacility.setPricing(Arrays.asList(facilityPricing,facilityPricing2,facilityPricing3));
        SportFacility savedFacility = service.createSportFacility(sportFacility);
    }


}
*/
