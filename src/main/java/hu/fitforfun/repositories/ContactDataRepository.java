package hu.fitforfun.repositories;

import hu.fitforfun.model.ContactData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDataRepository extends JpaRepository<ContactData, Long> {
}
