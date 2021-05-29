package hu.fitforfun.repositories;

import hu.fitforfun.model.instructor.TrainingSessionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSessionDetailsRepository extends JpaRepository<TrainingSessionDetails, Long> {
}
