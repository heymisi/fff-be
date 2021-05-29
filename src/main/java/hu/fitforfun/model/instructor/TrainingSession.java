package hu.fitforfun.model.instructor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "instructor_training_sessions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = TrainingSession.class)
public class TrainingSession extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "training_session_type")
    private String trainingSessionType;

    @Column(name = "day", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WeekDays day;

    @Column(name = "session_start", nullable = false)
    private Double sessionStart;

    @Column(name = "session_end", nullable = false)
    private Double sessionEnd;

    @Column(name = "monthly_price")
    private Integer monthlyPrice;

    @Column(name = "occasion_price")
    private Integer occasionPrice;

    @Column(name = "max_client_number", nullable = false)
    private Integer maxClientNumber;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany
    @JoinTable(name = "training_Session_Users",
            joinColumns = @JoinColumn(name = "training_session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"))
    private List<User> client;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    public TrainingSession() {
        this.client = new ArrayList<>();
    }
}
