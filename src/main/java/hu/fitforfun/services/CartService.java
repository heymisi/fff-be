package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.request.TransactionItemRequestModel;
import hu.fitforfun.model.shop.Cart;
import hu.fitforfun.model.shop.TransactionItem;
import hu.fitforfun.model.user.User;

public interface CartService {

    User addItemToCart(Long userId, TransactionItemRequestModel item) throws FitforfunException;

    Cart deleteItemFromCart(Long userId, Long itemId) throws FitforfunException;

    Cart getCartByUser(Long userId) throws FitforfunException;

    TransactionItem incrementTransactionItemQuantity(Long transactionItemId) throws FitforfunException;

    TransactionItem decrementTransactionItemQuantity(Long transactionItemId) throws FitforfunException;
}
