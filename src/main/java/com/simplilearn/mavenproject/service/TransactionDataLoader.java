package com.simplilearn.mavenproject.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class TransactionDataLoader {
    // Load transactions from a file
    public List<Transaction> getTransactions() throws IOException {
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
                String[] fields = line.split("\\d{2}/\\d{2}/\\d{4}");
                if (fields.length == 2) {
                	
                String amountStr = fields[1].trim();
                BigDecimal amount = new BigDecimal(amountStr);
                // Parse the transaction date from the line using a regular expression that matches the date format
                String[] dateFields = line.split("\\s+");
                LocalDate date = LocalDate.parse(dateFields[0], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String category = parts[2].trim();
                Transaction transaction = new Transaction(date, category, amount);
                transaction.setDescription(description);
                transaction.setAmount(amount);
                transaction.setCategory(category);
            }
            }           
        }
        return transactions;
    }
}