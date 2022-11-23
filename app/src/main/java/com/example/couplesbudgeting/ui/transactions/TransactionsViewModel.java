package com.example.couplesbudgeting.ui.transactions;

import androidx.lifecycle.ViewModel;

import com.example.couplesbudgeting.models.Transaction;
import com.example.couplesbudgeting.services.TransactionsService;

import java.util.Date;

public class TransactionsViewModel extends ViewModel {
    TransactionsService transactionsService = new TransactionsService();

    public void createTransaction(String name, String category, Double amount, Date state) {
        Transaction newTransaction = new Transaction(name, category, amount, state);
        transactionsService.createTransaction(newTransaction);
    }

    public Transaction getTransaction() {
        return transactionsService.getTransaction();
    }

    public void updateTransaction(String name, String category, Double amount, Date state) {
        //Get Transaction to update
        Transaction transactionToUpdate = transactionsService.getTransaction();

        //Update object
        transactionToUpdate.setName(name);
        transactionToUpdate.setCategory(category);
        transactionToUpdate.setAmount(amount);
        transactionToUpdate.setDate(state);

        //Update DB
        transactionsService.updateTransaction(transactionToUpdate);
    }

    public void deleteTransaction(int transactionID) {
        transactionsService.deleteTransaction(transactionID);
    }
}