package hu.fitforfun.repositories;

import hu.fitforfun.model.Comment;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    List<Comment> findByShopItemOrderByCreatedDesc(ShopItem shopItem);

    List<Comment> findByCommenterAndShopItem(User commenter, ShopItem shopItem);

    List<Comment> findByCommenterAndInstructor(User commenter, Instructor instructor);

    List<Comment> findByCommenterAndSportFacility(User commenter, SportFacility sportFacility);

}
