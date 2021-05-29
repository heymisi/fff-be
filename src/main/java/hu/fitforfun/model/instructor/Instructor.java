package hu.fitforfun.model.instructor;


import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.*;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructors")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Instructor.class)
public class Instructor extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "bio", length = 3000)
    private String bio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
    private List<Comment> comments;

    @ManyToMany()
    @JoinTable(name = "instructors_known_sports",
            joinColumns = @JoinColumn(name = "instructors_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sports_id", referencedColumnName = "id"))
    private List<SportType> knownSports;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
    private List<TrainingSession> trainingSessions;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rating;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @Lob
    @Column(name = "imageString")
    private String imageString;

    public Instructor addComment(Comment comment) {
        comment.setInstructor(this);
        this.comments.add(comment);
        return this;
    }

    public Instructor addSport(SportType sportType) {
        sportType.getInstructors().add(this);
        this.knownSports.add(sportType);
        return this;
    }

    public Instructor addTrainingSession(TrainingSession session) {
        session.setInstructor(this);
        this.trainingSessions.add(session);
        return this;
    }

}
