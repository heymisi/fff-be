package hu.fitforfun.model.address;

import hu.fitforfun.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cities")
public class City extends BaseEntity {
    @Column(name = "city_name")
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "county_id")
    private County county;
}
