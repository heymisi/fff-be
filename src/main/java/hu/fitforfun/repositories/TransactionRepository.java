package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> findByPurchaser(User purchaser);

    List<Transaction> findByPurchaserId(Long id);
}
