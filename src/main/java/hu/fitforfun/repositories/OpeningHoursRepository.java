package hu.fitforfun.repositories;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.model.facility.OpeningHours;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpeningHoursRepository extends PagingAndSortingRepository<OpeningHours, Long> {
    Optional<OpeningHours> findByDayAndOpenTimeAndCloseTime(WeekDays day, Double openTime, Double closeTime);
}
