package hu.fitforfun.repositories;

import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.address.City;
import hu.fitforfun.model.shop.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SportFacilityRepository extends PagingAndSortingRepository<SportFacility, Long>, JpaSpecificationExecutor<SportFacility> {

    Optional<SportFacility> findByName(String name);

    Page<SportFacility> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<SportFacility> findByAddressCity(City city, Pageable pageable);

    Page<SportFacility> findByAvailableSportsIdIn(List<Long> availableSportsId, Pageable pageable);

}