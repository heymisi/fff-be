package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.enums.TransactionStatus;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "transaction")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Transaction.class)
public class Transaction extends BaseEntity {

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaser_id")
    private User purchaser;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "transaction_created")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date transactionCreated;

    @Column(name = "sum_total")
    private Double sumTotal;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TransactionItem> transactionItems;

    public Transaction() {
        this.transactionItems = new ArrayList<>();
    }

    public void addItem(TransactionItem transactionItem) {
        if (transactionItem != null) {
            transactionItems.add(transactionItem);
            transactionItem.setTransaction(this);
        }
    }
}
