package hu.fitforfun.model.facility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "opening_hours")
public class OpeningHours extends BaseEntity {

    @Column(name = "day", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WeekDays day;

    @Column(name = "open_time", nullable = false)
    private Double openTime;

    @Column(name = "close_time", nullable = false)
    private Double closeTime;

    @Column(name = "is_closed_today")
    private Boolean isOpenNow = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;

}
