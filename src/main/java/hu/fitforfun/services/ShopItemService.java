package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.shop.ShopItem;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShopItemService {
    ShopItem getShopItemById(Long id) throws FitforfunException, IOException;

    Page<ShopItem> listShopItems(int page, int limit, String filter, Specification<ShopItem> spec);

    ShopItem createShopItem(ShopItem shopItem) throws FitforfunException;

    void deleteShopItem(Long id) throws FitforfunException;

    ShopItem updateShopItem(Long id, ShopItem shopItem) throws FitforfunException;

    List<Comment> getComments(Long id);

    Comment addComment(Long shopItemId, CommentRequestModel massage) throws FitforfunException;

    List<ShopItem> listShopItemsWithoutFilter();

    void addImage(Long id, MultipartFile multipartFile) throws Exception;

}
