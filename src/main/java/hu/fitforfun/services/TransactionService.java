package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.shop.TransactionItem;
import hu.fitforfun.model.user.User;

import java.util.List;

public interface TransactionService {
    Transaction getTransactionById(Long id) throws FitforfunException;

    List<Transaction> listTransactions();

    List<Transaction> listTransactionsByUser(User user);

    Transaction createTransaction(Long userId) throws Exception;

    void deleteTransaction(Long id) throws FitforfunException;

    List<TransactionItem> listTransactionItems();

    List<Transaction> listTransactionsByUser(Long id);

}
