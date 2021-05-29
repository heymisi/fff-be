package hu.fitforfun.model;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "comments")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment extends BaseEntity {

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne()
    @JoinColumn(name = "commenter_id")
    private User commenter;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;

    @Lob
    @Column(name = "text")
    private String text;

    @Column(name = "rate")
    private Integer rate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_item_id")
    private ShopItem shopItem;
}
