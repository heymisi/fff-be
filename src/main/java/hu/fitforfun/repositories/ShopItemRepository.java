package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopItemRepository extends PagingAndSortingRepository<ShopItem, Long>, JpaSpecificationExecutor<ShopItem> {
    Optional<ShopItem> findByName(String name);

    Page<ShopItem> findByCategoryId(Long id, Pageable pageable);

    Page<ShopItem> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<ShopItem> findByOrderByPriceAsc(Pageable pageable);

    Page<ShopItem> findByOrderByPriceDesc(Pageable pageable);

    Page<ShopItem> findByOrderByDateCreatedAsc(Pageable pageable);

    Page<ShopItem> findByOrderByRatingDesc(Pageable pageable);

}
