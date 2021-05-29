package hu.fitforfun.controller;

import hu.fitforfun.model.shop.ItemCategory;
import hu.fitforfun.repositories.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item-categories")
public class ItemCategoryController {

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @GetMapping("")
    @Cacheable("categories")
    public List<ItemCategory> getCategories() {
        return itemCategoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ItemCategory getCategoryById(@PathVariable Long id) {
        return itemCategoryRepository.findById(id).get();
    }

    @GetMapping("/byName/{name}")
    public ItemCategory getCategoryByName(@PathVariable String name) {
        return itemCategoryRepository.findByCategoryName(name).get();
    }
}
