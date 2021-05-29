package hu.fitforfun.repositories;

import hu.fitforfun.model.facility.FacilityPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityPricingRepository extends JpaRepository<FacilityPricing, Long> {
}
