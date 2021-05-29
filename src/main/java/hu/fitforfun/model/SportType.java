package hu.fitforfun.model;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.shop.ShopItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "sports")
public class SportType extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "availableSports")
    private List<SportFacility> sportFacilities;

    @JsonIgnore
    @ManyToMany(mappedBy = "knownSports")
    private List<Instructor> instructors;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<ShopItem> items;

    public SportType() {
        sportFacilities = new ArrayList<>();
        instructors = new ArrayList<>();
        items = new HashSet<>();
    }
}
