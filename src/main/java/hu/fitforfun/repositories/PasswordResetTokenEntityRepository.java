package hu.fitforfun.repositories;

import hu.fitforfun.model.user.PasswordResetTokenEntity;
import hu.fitforfun.model.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PasswordResetTokenEntityRepository extends PagingAndSortingRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findByToken(String token);
    List<PasswordResetTokenEntity> findByUserDetails(User userDetails);
}
