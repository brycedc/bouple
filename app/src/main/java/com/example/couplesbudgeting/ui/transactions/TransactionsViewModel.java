package com.example.couplesbudgeting.ui.transactions;

import androidx.lifecycle.ViewModel;

import com.example.couplesbudgeting.models.Transaction;
import com.example.couplesbudgeting.services.TransactionsService;

import java.util.Date;
import java.util.List;

public class TransactionsViewModel extends ViewModel {
    TransactionsService transactionsService = new TransactionsService();

    public void createTransaction(String name, String category, Double amount, Date date) {
        Transaction newTransaction = new Transaction(name, category, amount, date);
        transactionsService.createTransaction(newTransaction);
    }

    public void getAllUserTransactions(TransactionsFragment.TransactionsList transactionsReturn) {
        transactionsService.getAllUserTransactions(transactionsReturn);
    }

    public List<Transaction> getAllUserTransactions() {
        return transactionsService.getAllUserTransactions();
    }

    public void updateTransaction(String name, String category, Double amount, Date date) {
        //Get Transaction to update
        Transaction transactionToUpdate = transactionsService.getTransaction();

        //Update object
        transactionToUpdate.setName(name);
        transactionToUpdate.setCategory(category);
        transactionToUpdate.setAmount(amount);
        transactionToUpdate.setDate(date);

        //Update DB
        transactionsService.updateTransaction("", transactionToUpdate);
    }

    public void deleteTransaction(String transactionID) {
        transactionsService.deleteTransaction(transactionID);
    }


}