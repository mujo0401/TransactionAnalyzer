package com.simplilearn.mavenproject; 
import com.simplilearn.mavenproject.domain.Transaction;
import com.simplilearn.mavenproject.service.TransactionAnalyzer;
import com.simplilearn.mavenproject.service.TransactionDataLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpendingAnalyzerApp {

    public static void main(String[] args) {
        // Load transaction data
        TransactionDataLoader dataLoader = new TransactionDataLoader();
        List<Transaction> transactions;
        try {
            transactions = dataLoader.loadTransactions();
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if failed to load transactions
        }

        // Categorize transactions
        Map<String, List<Transaction>> categories = categorizeTransactions(transactions);

        // Analyze transactions
        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        analyzer.analyzeTransactions(categories);
    }

    // Categorize transactions
    private static Map<String, List<Transaction>> categorizeTransactions(List<Transaction> transactions) {
        Map<String, List<Transaction>> categorizedTransactions = new HashMap<>();

        // Implement categorization logic here
        // Example implementation: Categorize transactions based on their description

        for (Transaction transaction : transactions) {
            String category = getCategory(transaction); // Get category based on transaction properties

            if (!categorizedTransactions.containsKey(category)) {
                categorizedTransactions.put(category, new ArrayList<>()); // Use ArrayList
            }

            categorizedTransactions.get(category).add(transaction);
        }

        return categorizedTransactions;
    }

    // Helper method to get category based on transaction properties
    private static String getCategory(Transaction transaction) {
        String description = transaction.getDescription().toLowerCase(); // Get transaction description and convert to lowercase for case-insensitive comparison

        if (description.contains("grocery") || description.contains("supermarket")) {
            return "Grocery";
        } else if (description.contains("restaurant") || description.contains("dining")) {
            return "Restaurant";
        } else if (description.contains("shopping") || description.contains("retail")) {
            return "Shopping";
        } else if (description.contains("travel") || description.contains("hotel") || description.contains("flight")) {
            return "Travel";
        } else {
            return "Other";
        }
    }
}