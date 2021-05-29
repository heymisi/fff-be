package hu.fitforfun.model.request;

import hu.fitforfun.model.facility.SportFacility;
import lombok.Data;

import java.util.List;

@Data
public class InstructorRegistrationModel {
    private UserRegistrationModel user;
    private String title;
    private String bio;
    private List<Long> sportIds;
    private Long facilityId;
}
