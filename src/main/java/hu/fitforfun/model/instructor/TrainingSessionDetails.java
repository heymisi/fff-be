package hu.fitforfun.model.instructor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_session_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TrainingSessionDetails extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "monthly_price")
    private Integer monthlyPrice;

    @Column(name = "occasion_price")
    private Integer occasionPrice;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
