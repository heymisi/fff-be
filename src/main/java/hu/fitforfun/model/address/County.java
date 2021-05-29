package hu.fitforfun.model.address;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@ToString
@Table(name = "counties")
public class County extends BaseEntity {
    @Column(name = "county_name")
    private String countyName;

    @JsonIgnore
    @OneToMany(mappedBy = "county")
    List<City> cities;
}
