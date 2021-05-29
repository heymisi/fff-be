package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.Rating;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.shop.TransactionItem;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.ShopItemService;
import hu.fitforfun.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopItemServiceImpl implements ShopItemService {

    @Autowired
    ShopItemRepository shopItemRepository;


    @Autowired
    TransactionItemRepository transactionItemRepository;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    @Override
    public ShopItem getShopItemById(Long id) throws FitforfunException, IOException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        return setImage(optionalShopItem.get());
    }

    @Override
    public Page<ShopItem> listShopItems(int page, int limit, String filter, Specification<ShopItem> spec) {
        Pageable pageableRequest = PageRequest.of(page, limit);

        if (filter.equals("priceBackward"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("price").descending());

        else if (filter.equals("priceForward"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("price").ascending());

        else if (filter.equals("byRate"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("rating.value").descending());

        else if (filter.equals("newest"))
            pageableRequest = PageRequest.of(page, limit, Sort.by("dateCreated").descending());

        return shopItemRepository.findAll(spec, pageableRequest).
                map(item -> setImage(item));

    }

    public List<ShopItem> listShopItemsWithoutFilter() {
        List<ShopItem> returnValue = new ArrayList<>();
        shopItemRepository.findAll().forEach(returnValue::add);
        return returnValue.stream().map(item -> setImage(item)).collect(Collectors.toList());
    }

    @Override
    public ShopItem createShopItem(ShopItem shopItem) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findByName(shopItem.getName());
        if (optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_ALREADY_EXISTS);
        }
        shopItem.setRating(new Rating());
        shopItem.setComments(new ArrayList());
        return shopItemRepository.save(shopItem);
    }

    @Override
    public void deleteShopItem(Long id) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_ALREADY_EXISTS);
        }
        if (transactionItemRepository.findByShopItem(optionalShopItem.get()).isPresent()) {
            throw new FitforfunException(ErrorCode.PRODUCT_ALREADY_IN_USE);
        }
        shopItemRepository.delete(optionalShopItem.get());
    }

    @Override
    public ShopItem updateShopItem(Long id, ShopItem shopItem) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_ALREADY_EXISTS);
        }
        ShopItem updatedShopItem = optionalShopItem.get();
        if (shopItem.getName() != null) {
            updatedShopItem.setName(shopItem.getName());
        }
        if (shopItem.getCategory() != null) {
            updatedShopItem.setCategory(shopItem.getCategory());
        }
        if (shopItem.getDescription() != null) {
            updatedShopItem.setDescription(shopItem.getDescription());
        }
        if (shopItem.getPrice() != null) {
            updatedShopItem.setPrice(shopItem.getPrice());
        }
        if (shopItem.getUnitsInStock() != null) {
            updatedShopItem.setUnitsInStock(shopItem.getUnitsInStock());
        }
        if (shopItem.getSportType() != null) {
            updatedShopItem.setSportType(shopItem.getSportType());
        }
        if (shopItem.getImage() != null) {
            updatedShopItem.setImage(shopItem.getImage());
        }
        return shopItemRepository.save(updatedShopItem);
    }

    @Override
    public List<Comment> getComments(Long id) {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            return null;
        }
        ShopItem shopItem = optionalShopItem.get();
        return commentRepository.findByShopItemOrderByCreatedDesc(shopItem);
    }

    @Override
    public Comment addComment(Long shopItemId, CommentRequestModel comment) throws FitforfunException {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(shopItemId);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        Optional<User> optionalUser = userRepository.findById((long) comment.getUserId());
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        ShopItem shopItem = optionalShopItem.get();
        List<Comment> optionalComment = commentRepository.findByCommenterAndShopItem(optionalUser.get(), shopItem);
        if (optionalComment.size() != 0) {
            throw new FitforfunException(ErrorCode.ALREADY_COMMENTED);
        }

        shopItem.getRating().setValue((shopItem.getRating().getValue() * shopItem.getRating().getCounter()
                + comment.getRate()) / (shopItem.getRating().getCounter() + 1));

        shopItem.getRating().setCounter(shopItem.getRating().getCounter() + 1);
        Comment commentToAdd = new Comment();
        commentToAdd.setText(comment.getMessage());
        commentToAdd.setRate(comment.getRate());
        commentToAdd.setCommenter(optionalUser.get());
        commentRepository.save(commentToAdd);
        shopItem.addComment(commentToAdd);
        shopItemRepository.save(shopItem);
        return commentToAdd;
    }

    @Override
    public void addImage(Long id, MultipartFile multipartFile) throws Exception {
        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(id);
        if (!optionalShopItem.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        ShopItem shopitem = optionalShopItem.get();
        shopitem.setImage(new Image(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                ImageUtils.compressBytes(multipartFile.getBytes())));
        shopItemRepository.save(shopitem);
    }

    private ShopItem setImage(ShopItem item) {
        if (item.getImage() != null) {
            try {
                Image image = imageService.getImageById(item.getImage().getId());
                item.setImageString("data:" + image.getType() + ";base64," + Base64.getEncoder().encodeToString(image.getPicByte()));
                item.setImage(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

}
