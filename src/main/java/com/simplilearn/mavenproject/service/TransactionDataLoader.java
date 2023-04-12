package com.simplilearn.mavenproject.service;


import com.simplilearn.mavenproject.domain.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class TransactionDataLoader {


    // Load transactions from a file
    public List<Transaction> loadTransactions() throws IOException {
        List<Transaction> transactions = new ArrayList<>();

        // Read transaction data from a file, CSV, JSON, or other formats
        // and convert it into Transaction objects
        try (BufferedReader br = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String description = parts[0].trim();
                String value = parts[1].trim();
                if (!value.matches("[0-9]+([.][0-9]+)?([eE][+-]?[0-9]+)?")) {
                    // Skip this line or throw an exception for invalid value format
                    continue;
                }
                BigDecimal amount = new BigDecimal(value);
                String category = parts[2].trim();
                Transaction transaction = new Transaction(null, category, amount);
                transaction.setDescription(description);
                transaction.setAmount(amount);
                transaction.setCategory(category);
            } 
        }

        return transactions;
    }
}