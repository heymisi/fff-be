package hu.fitforfun.repositories;

import hu.fitforfun.model.SportType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportTypeRepository extends PagingAndSortingRepository<SportType, Long> {
    Optional<SportType> findByNameIgnoreCase(String name);
}
