package com.simplilearn.mavenproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.simplilearn.mavenproject.domain.Transaction;

public class TransactionExtractor {
    private final Map<String, String> categories;

    public TransactionExtractor(String document) {
        if (document == null || document.isEmpty()) {
            throw new IllegalArgumentException("Document cannot be null or empty.");
        }
        // Create categories map based on document
        categories = new HashMap<>();
        String[] lines = document.split("\\r?\\n");
        if (lines.length == 0) {
            throw new IllegalArgumentException("Document must contain at least one line.");
        }
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                categories.put(parts[0].trim().toLowerCase(), parts[1].trim());
            }
        }
    }
        
    public Map<String, List<Transaction>> categorizeTransactions(List<Transaction> transactions) {
        Map<String, List<Transaction>> categorizedTransactions = new HashMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.getDescription() == null) continue;
            String description = transaction.getDescription().toLowerCase();
            String category = "Other";
            for (Map.Entry<String, String> entry : categories.entrySet()) {
                if (description.contains(entry.getKey().toLowerCase())) {
                    category = entry.getValue();
                    break;
                }
            }
            if (!categorizedTransactions.containsKey(category)) {
                categorizedTransactions.put(category, new ArrayList<>());
            }
            categorizedTransactions.get(category).add(transaction);
        }
        return categorizedTransactions;
    }

    // This method should be implemented
    public List<Transaction> extractTransactions(PDDocument pdfDocument) {
        return new ArrayList<>();
    }
}