package com.simplilearn.mavenproject.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionCategorizer {
    private final List<Transaction> transactions;
    public TransactionCategorizer(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public Map<String, List<Transaction>> categorizeTransactions() {
        Map<String, List<Transaction>> categorizedTransactions = new HashMap<>();       
        for (Transaction transaction : transactions) {
            String category = categorizeTransaction(transaction);
            categorizedTransactions.computeIfAbsent(category, k -> new ArrayList<>()).add(transaction);            
        }
        return categorizedTransactions;
    }
    public String categorizeTransaction(Transaction transaction) {
        // Categorize transaction logic here
        return "Uncategorized";
    }
}