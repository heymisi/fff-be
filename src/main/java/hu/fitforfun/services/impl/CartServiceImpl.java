package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.request.TransactionItemRequestModel;
import hu.fitforfun.model.shop.Cart;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.shop.TransactionItem;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.CartRepository;
import hu.fitforfun.repositories.ShopItemRepository;
import hu.fitforfun.repositories.TransactionItemRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopItemRepository shopItemRepository;
    @Autowired
    private TransactionItemRepository transactionItemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public User addItemToCart(Long userId, TransactionItemRequestModel item) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        User user = optionalUser.get();

        Optional<ShopItem> optionalShopItem = shopItemRepository.findById(item.getShopItemId());
        if (!optionalShopItem.isPresent())
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);

        ShopItem shopItem = optionalShopItem.get();
        Cart userCart = user.getCart();
        boolean isItemAlreadyIn = false;

        for (TransactionItem transactionItem1 : userCart.getTransactionItems()) {
            if (transactionItem1.getShopItem().equals(shopItem)) {
                transactionItem1.setQuantity(transactionItem1.getQuantity() + 1);
                transactionItem1.setPrice(transactionItem1.getShopItem().getPrice().doubleValue() * transactionItem1.getQuantity());
                isItemAlreadyIn = true;
            }
        }
        if (isItemAlreadyIn) {
            computeCartData(userCart);
            return userRepository.save(user);
        }
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setShopItem(shopItem);
        transactionItem.setQuantity(item.getQuantity());
        transactionItem.setPrice(shopItem.getPrice().doubleValue() * item.getQuantity());
        transactionItem.setCart(user.getCart());

        userCart.getTransactionItems().add(transactionItem);
        computeCartData(userCart);
        return userRepository.save(user);
    }

    @Override
    public Cart deleteItemFromCart(Long userId, Long itemId) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        User user = optionalUser.get();

        Optional<TransactionItem> optionalItem = transactionItemRepository.findById(itemId);
        if (!optionalItem.isPresent())
            throw new FitforfunException("TRANSACTION_ITEM_NOT_EXISTS");

        Cart userCart = user.getCart();
        userCart.getTransactionItems().remove(optionalItem.get());
        transactionItemRepository.delete(optionalItem.get());

        computeCartData(userCart);
        return userCart;
    }

    @Override
    public Cart getCartByUser(Long userId) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        User user = optionalUser.get();
        return user.getCart();
    }

    @Override
    public TransactionItem incrementTransactionItemQuantity(Long transactionItemId) throws FitforfunException {
        Optional<TransactionItem> transactionItemOptional = transactionItemRepository.findById(transactionItemId);
        if (!transactionItemOptional.isPresent())
            throw new FitforfunException("TRANSACTION_ITEM_NOT_PRESENT");

        TransactionItem transactionItem = transactionItemOptional.get();
        transactionItem.setQuantity(transactionItem.getQuantity() + 1);
        transactionItem.setPrice(transactionItem.getShopItem().getPrice().doubleValue() * transactionItem.getQuantity());

        transactionItemRepository.save(transactionItem);

        computeCartDataByTransactionItem(transactionItem);
        return transactionItem;
    }

    @Override
    public TransactionItem decrementTransactionItemQuantity(Long transactionItemId) throws FitforfunException {
        Optional<TransactionItem> transactionItemOptional = transactionItemRepository.findById(transactionItemId);
        if (!transactionItemOptional.isPresent())
            throw new FitforfunException("TRANSACTION_ITEM_NOT_PRESENT");
        TransactionItem transactionItem = transactionItemOptional.get();

        if (transactionItem.getQuantity() == 1) return transactionItem;

        transactionItem.setQuantity(transactionItem.getQuantity() - 1);
        transactionItem.setPrice(transactionItem.getShopItem().getPrice().doubleValue() * transactionItem.getQuantity());

        transactionItemRepository.save(transactionItem);

        computeCartDataByTransactionItem(transactionItem);
        return transactionItem;
    }

    private void computeCartData(Cart cart) {
        cart.setTotalPrice(0);
        cart.getTransactionItems().forEach(userCartItem ->
                cart.setTotalPrice(cart.getTotalPrice() + userCartItem.getPrice().intValue()));
        cart.setCartItemQuantity(cart.getTransactionItems().size());

        userRepository.save(userRepository.findByCart(cart));
    }

    private void computeCartDataByTransactionItem(TransactionItem transactionItem) {
        Cart cart = cartRepository.findByTransactionItemsIn(Arrays.asList(transactionItem));
        computeCartData(cart);
    }
}
