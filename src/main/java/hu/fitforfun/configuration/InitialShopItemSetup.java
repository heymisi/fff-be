/*
package hu.fitforfun.configuration;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Rating;
import hu.fitforfun.model.SportType;
import hu.fitforfun.model.shop.ItemCategory;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.ShopItemService;
import hu.fitforfun.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitialShopItemSetup {
    @Autowired
    ShopItemService shopItemService;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TransactionItemRepository transactionItemRepository;

    @Autowired
    ShopItemRepository shopItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SportTypeRepository sportTypeRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {
        ItemCategory category1 = new ItemCategory();
        category1.setCategoryName("Cipők");
        itemCategoryRepository.save(category1);

        ItemCategory category3 = new ItemCategory();
        category3.setCategoryName("Kiegészítők");
        itemCategoryRepository.save(category3);

        ItemCategory category2 = new ItemCategory();
        category2.setCategoryName("Sport kellékek");
        itemCategoryRepository.save(category2);

        ItemCategory category4 = new ItemCategory();
        category4.setCategoryName("Ruhák");
        itemCategoryRepository.save(category4);

        SportType sportType = new SportType();
        sportType.setName("Labdarugás");
        sportTypeRepository.save(sportType);

        SportType sportType2 = new SportType();
        sportType2.setName("Kosárlabda");
        sportTypeRepository.save(sportType2);

        SportType sportType3 = new SportType();
        sportType3.setName("Testépítés");
        sportTypeRepository.save(sportType3);

        SportType sportType222 = new SportType();
        sportType222.setName("Szabadidős sport");
        sportTypeRepository.save(sportType222);


        SportType running = new SportType();
        running.setName("Futás");
        sportTypeRepository.save(running);
        List<SportType> sportTypes = new ArrayList<>();
        sportTypeRepository.findAll().forEach(sportTypes::add);

        ShopItem shopItem1 = null;
        ShopItem shopItem2 = null;
        try {
            shopItem1 = shopItemService.createShopItem(create("item1", "100", category3, sportType2));
            shopItem2 = shopItemService.createShopItem(create("item2", "101", category3, sportType3));

            shopItemService.createShopItem(create("item3", "102", category3, sportType));
            shopItemService.createShopItem(create("item4", "103", category1, sportType));
            shopItemService.createShopItem(create("item5", "104", category1, sportType2));
            shopItemService.createShopItem(create("item6", "105", category1, sportType3));
            shopItemService.createShopItem(create("item7", "106", category1, sportType));
            shopItemService.createShopItem(create("item8", "107", category1, sportType));
            shopItemService.createShopItem(create("item9", "101", category3, sportType3));
            shopItemService.createShopItem(create("item10", "102", category3, sportType));

        } catch (FitforfunException e) {
            e.printStackTrace();
        }
        shopItemRepository.save(shopItem1);

    }

    private ShopItem create(String name, String price, ItemCategory category2, SportType sportType) {
        ShopItem item8 = new ShopItem();
        item8.setUnitsInStock(10);
        item8.setCategory(category2);
        item8.setDescription("desc");
        item8.setName(name);
        item8.setPrice(new BigDecimal(price));
        Rating rating = new Rating();
        rating.setCounter(0);
        rating.setValue(0D);
        item8.setRating(rating);
        item8.setSportType(sportType);

        return item8;
    }
}
*/
