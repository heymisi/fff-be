package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.Cart;
import hu.fitforfun.model.shop.TransactionItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findByTransactionItemsIn(List<TransactionItem> transactionItems);
}
