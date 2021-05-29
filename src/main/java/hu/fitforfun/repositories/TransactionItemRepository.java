package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.shop.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
    Optional<TransactionItem> findByShopItem(ShopItem shopItem);
}
