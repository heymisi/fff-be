package hu.fitforfun.controller;

import hu.fitforfun.exception.Response;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.shop.TransactionItem;
import hu.fitforfun.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    
    @CacheEvict(cacheNames = "users",allEntries = true)
    @GetMapping("/purchase/{userId}")
    public Response purchase(@PathVariable Long userId) {
        try {
            return Response.createOKResponse(transactionService.createTransaction(userId));
        } catch (Exception e) {
            return Response.createErrorResponse("Error during purhcase");
        }
    }

    @GetMapping("")
    public List<Transaction> getAll() {
        return transactionService.listTransactions();
    }

    @GetMapping("/transactionItems")
    public List<TransactionItem> getTransactionItems() {
        return transactionService.listTransactionItems();
    }

    @GetMapping("/{userId}")
    public List<Transaction> getTransactionByUser(@PathVariable Long userId) {
        return transactionService.listTransactionsByUser(userId);
    }
}
