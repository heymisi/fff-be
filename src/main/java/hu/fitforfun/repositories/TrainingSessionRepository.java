package hu.fitforfun.repositories;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByInstructor(Instructor instructor);

    List<TrainingSession> findByInstructorAndDay(Instructor instructor, WeekDays day);

    List<TrainingSession> findByClientAndDay(User client, WeekDays day);

    List<TrainingSession> findByClientIdIn(List<Long> id);

    Optional<TrainingSession> findByInstructorAndDayAndSessionStart(Instructor instructor, WeekDays day, Double sessionStart);

    Optional<TrainingSession> findByInstructorAndDayAndSessionEnd(Instructor instructor, WeekDays day, Double sessionEnd);

    Optional<TrainingSession> findByClientAndDayAndSessionStart(User client, WeekDays day, Double sessionStart);

    Optional<TrainingSession> findByClientAndDayAndSessionEnd(User client, WeekDays day, Double sessionEnd);
}
