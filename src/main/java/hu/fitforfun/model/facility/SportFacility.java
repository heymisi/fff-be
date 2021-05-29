package hu.fitforfun.model.facility;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.*;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.instructor.Instructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "facility")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = SportFacility.class)
public class SportFacility extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactData contactData;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<OpeningHours> openingHours;

    @ManyToMany()
    @JoinTable(name = "facility_available_sports",
            joinColumns = @JoinColumn(name = "facilities_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sports_id", referencedColumnName = "id"))
    private List<SportType> availableSports;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "sportFacility")
    private List<Instructor> instructors;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<FacilityPricing> pricing;

    @OneToOne(cascade = CascadeType.ALL)
    private Image profileImage;

    @Lob
    @Column(name = "profile_image_string")
    private String profileImageString;

    @OneToOne(cascade = CascadeType.ALL)
    private Image mapImage;

    @Lob
    @Column(name = "map_image_string")
    private String mapImageString;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rating;

    public SportFacility addComment(Comment comment) {
        comment.setSportFacility(this);
        this.comments.add(comment);
        return this;
    }

    public SportFacility addInstructor(Instructor instructor) {
        instructor.setSportFacility(this);
        this.instructors.add(instructor);
        return this;
    }

    public SportFacility addSport(SportType sportType) {
        sportType.getSportFacilities().add(this);
        this.availableSports.add(sportType);
        return this;
    }

    public SportFacility() {
        instructors = new ArrayList<>();
    }
}
