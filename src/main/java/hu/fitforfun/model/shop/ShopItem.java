package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "shop_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = ShopItem.class)
public class ShopItem extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = true)
    private BigDecimal price;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdated;

    @Column(name = "units_in_stock", nullable = true)
    private Integer unitsInStock;

    @Lob
    @Column(name = "description", nullable = true)
    private String description;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private SportType sportType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shopItem")
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @Lob
    @Column(name = "image_string")
    private String imageString;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rating;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "shopItem")
    private List<TransactionItem> transactionItems;

    public ShopItem addComment(Comment comment) {
        comment.setShopItem(this);
        this.comments.add(comment);
        return this;
    }
}
