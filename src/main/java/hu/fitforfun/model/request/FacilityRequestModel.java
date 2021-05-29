package hu.fitforfun.model.request;

import hu.fitforfun.model.Image;
import hu.fitforfun.model.SportType;
import hu.fitforfun.model.facility.FacilityPricing;
import hu.fitforfun.model.facility.OpeningHours;
import lombok.Data;

import java.util.List;

@Data
public class FacilityRequestModel {
    private String name;
    private String email;
    private String mobile;
    private String city;
    private String street;
    private List<OpeningHours> openingHours;
    private List<SportType> availableSports;
    private List<Long> instructors;
    private List<FacilityPricing> pricing;
    private String description;
    private Image profileImage;
    private Image mapImage;
}
