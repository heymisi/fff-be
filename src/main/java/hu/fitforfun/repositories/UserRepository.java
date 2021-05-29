package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.Cart;
import hu.fitforfun.model.user.Role;
import hu.fitforfun.model.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByContactDataEmail(String email);

    List<User> findByRolesIn(List<Role> role);

    User findUserByEmailVerificationToken(String token);

    User findByCart(Cart cart);
}
