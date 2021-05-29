package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "cart")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Cart.class)
public class Cart extends BaseEntity {

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<TransactionItem> transactionItems;

    @Column(name = "cart_item_quantity")
    private Integer cartItemQuantity;

    @Column(name = "total_price")
    private Integer totalPrice;

    public Cart() {
        this.transactionItems = new ArrayList<>();
        this.cartItemQuantity = 0;
        this.totalPrice = 0;
    }

}
