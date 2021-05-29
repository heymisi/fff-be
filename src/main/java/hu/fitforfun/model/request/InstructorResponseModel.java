package hu.fitforfun.model.request;

import lombok.Data;

import java.util.List;

@Data
public class InstructorResponseModel {
    String bio;
    String title;
    List<Long> knownSports;
    Long sportFacility;
}
