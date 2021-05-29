package hu.fitforfun.model.facility;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "facility_pricing")
public class FacilityPricing extends BaseEntity {

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "session_ticket_price")
    private Double sessionTicketPrice;

    @Column(name = "single_ticket_price")
    private Double singleTicketPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;
}
