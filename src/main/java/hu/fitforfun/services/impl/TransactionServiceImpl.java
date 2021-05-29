package hu.fitforfun.services.impl;

import hu.fitforfun.enums.TransactionStatus;
import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.shop.Cart;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.shop.TransactionItem;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.TransactionItemRepository;
import hu.fitforfun.repositories.TransactionRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.EmailService;
import hu.fitforfun.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionItemRepository transactionItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Override
    public Transaction getTransactionById(Long id) throws FitforfunException {

        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (!optionalTransaction.isPresent()) {
            throw new FitforfunException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }
        return optionalTransaction.get();
    }

    @Override
    public List<Transaction> listTransactions() {
        List<Transaction> returnValue = new ArrayList<>();

        transactionRepository.findAll().forEach(returnValue::add);
        return returnValue;
    }

    @Override
    public List<Transaction> listTransactionsByUser(User user) {
        return transactionRepository.findByPurchaser(user);
    }

    @Override
    public Transaction createTransaction(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        User user = optionalUser.get();

        Transaction createdTransaction = new Transaction();
        createdTransaction.setPurchaser(user);
        createdTransaction.setSumTotal(user.getCart().getTotalPrice().doubleValue());
        createdTransaction.setStatus(TransactionStatus.PENDING);
        createdTransaction.setTrackingNumber(UUID.randomUUID().toString());

        user.getCart().getTransactionItems().forEach(transactionItem -> {
            transactionItem.setCart(null);
            createdTransaction.addItem(transactionItem);
        });

        user.setCart(new Cart());
        transactionRepository.save(createdTransaction);
        emailService.sendOrderRecapMail(createdTransaction);
        return createdTransaction;
    }

    @Override
    public void deleteTransaction(Long id) throws FitforfunException {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (!optionalTransaction.isPresent()) {
            throw new FitforfunException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }
        transactionRepository.delete(optionalTransaction.get());
    }

    public List<TransactionItem> listTransactionItems() {
        List<TransactionItem> returnValue = new ArrayList<>();
        transactionItemRepository.findAll().forEach(returnValue::add);
        return returnValue;
    }

    @Override
    public List<Transaction> listTransactionsByUser(Long id) {
        return transactionRepository.findByPurchaserId(id);
    }

}
