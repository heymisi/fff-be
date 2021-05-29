package hu.fitforfun.repositories;

import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends PagingAndSortingRepository<Instructor, Long>, JpaSpecificationExecutor<Instructor> {
    Optional<Instructor> findByUser(User user);

    Page<Instructor> findByKnownSportsIdIn(List<Long> availableSportsId, Pageable pageable);
}