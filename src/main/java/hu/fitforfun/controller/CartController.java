package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.request.TransactionItemRequestModel;
import hu.fitforfun.model.shop.Cart;
import hu.fitforfun.model.user.User;
import hu.fitforfun.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCartByUser(@PathVariable Long userId){
        try {
            return cartService.getCartByUser(userId);
        } catch (FitforfunException e) {
            return null;
        }
    }

    @CacheEvict(cacheNames = {"users","shop-item"},allEntries = true)
    @PostMapping("/addToCart/{userId}")
    public User addItemToCart(@PathVariable Long userId, @RequestBody TransactionItemRequestModel item) {
        try {
            return cartService.addItemToCart(userId, item);
        } catch (FitforfunException e) {
            return null;
        }
    }
    @CacheEvict(cacheNames = "users",allEntries = true)
    @GetMapping("/{userId}/deleteFromCart/{itemId}")
    public Response deleteItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        try {
            return Response.createOKResponse(cartService.deleteItemFromCart(userId, itemId));
        } catch (FitforfunException e) {
            return Response.createErrorResponse("error during delete item from cart");
        }
    }
    @CacheEvict(cacheNames = "users",allEntries = true)
    @GetMapping("/{transactionItemId}/incrementTransactionItemQuantity")
    public Response incrementTransactionItemQuantity(@PathVariable Long transactionItemId) {
        try {
            return Response.createOKResponse(cartService.incrementTransactionItemQuantity(transactionItemId));
        } catch (FitforfunException e) {
            return Response.createErrorResponse("error during increment quantity");
        }
    }
    @CacheEvict(cacheNames = "users",allEntries = true)
    @GetMapping("/{transactionItemId}/decrementTransactionItemQuantity")
    public Response decrementTransactionItemQuantity(@PathVariable Long transactionItemId) {
        try {
            return Response.createOKResponse(cartService.decrementTransactionItemQuantity(transactionItemId));
        } catch (FitforfunException e) {
            return Response.createErrorResponse("error during decrement quantity");
        }
    }

}
