package com.simplilearn.mavenproject.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransactionAnalyzer {

    public void analyzeTransactions(Map<String, List<Transaction>> categories) {
        // Perform data analysis tasks on the parsed transactions
        categories = new HashMap<>(); // Initialize categories
        // Categorize transactions
        categorizeTransactions(categories);

        // Calculate spending totals, averages, and trends
        calculateSpendingStatistics(categories);

        // Generate spending reports or summaries
        generateSpendingReports(categories);
    }

    private void categorizeTransactions(Map<String, List<Transaction>> categories) {
        // Categorize transactions based on pre-defined rules or criteria
        // Update the category field of each transaction object accordingly

        // Example implementation:
        // You can define rules or criteria for categorizing transactions, e.g., based on keywords, transaction types,
        // or transaction amounts. You can then loop through the transactions and update their category field accordingly.

        // Loop through each category
        for (Map.Entry<String, List<Transaction>> entry : categories.entrySet()) {
            List<Transaction> transactions = entry.getValue();

            // Loop through each transaction in the category
            for (Transaction transaction : transactions) {
                // Perform categorization logic, e.g., based on keywords or transaction types
                String description = transaction.getDescription();

                if (description.contains("Sling TV") || description.contains("Prime Video") || description.contains("Netflix") || description.contains("ESPN Plus") || description.contains("Audible") || description.contains("Patreon") || description.contains("Endel Soun")) {
                    transaction.setCategory("Streaming Services");
                } else if (description.contains("ATT") || description.contains("MULTIFAMILY UTILIT") || description.contains("LAUNDRY KIOSK") ) {
                    transaction.setCategory("Utilities");
                } else if (description.contains("ALLSTATE") || description.contains("STATE FARM ") || description.contains("XCEL ENERGY")) {
                    transaction.setCategory("Insurance");
                } else if (description.contains("NORTH MEMORIAL") || description.contains("MAYO CLINIC") || description.contains("PARK NICOLLET")) {
                    transaction.setCategory("Medical");
                } else if (description.contains("MAINSTREAM CBD")) {
                    transaction.setCategory("Entertainment");
                } else if (description.contains("LUNDS&BYERLYS") || description.contains("CUB FOODS")) {
                    transaction.setCategory("Groceries");
                } else if (description.contains("FETCH")) {
                    transaction.setCategory("Pets");
                } else {
                    transaction.setCategory("Other");
                }
            }
        }
    }

    private void calculateSpendingStatistics(Map<String, List<Transaction>> categories) {
        // Calculate spending totals, averages, and trends for each category
        // Update the relevant fields or data structures to store the calculated statistics

        // Loop through each category
        for (Map.Entry<String, List<Transaction>> entry : categories.entrySet()) {
            String category = entry.getKey();
            List<Transaction> transactions = entry.getValue();

            BigDecimal totalSpending  = BigDecimal.valueOf(0.0);;
            int numTransactions = transactions.size();
            BigDecimal averageSpending  = BigDecimal.valueOf(0.0);;
            BigDecimal spendingTrend = BigDecimal.valueOf(0.0);

            // Calculate total spending and average spending
            for (Transaction transaction : transactions) {
                totalSpending = totalSpending.add(transaction.getAmount());
            }
        
            if (numTransactions > 0) {
                averageSpending = totalSpending;
            }

            // Calculate spending trend
            if (numTransactions >= 2) {
                BigDecimal firstTransactionAmount = transactions.get(0).getAmount();
                BigDecimal lastTransactionAmount = transactions.get(numTransactions - 1).getAmount();
                spendingTrend = lastTransactionAmount.subtract(firstTransactionAmount);
            }

            // Update the category object with the calculated statistics
            Category categoryObj = new Category(category);
            categoryObj.setTotalSpending(totalSpending);
            categoryObj.setAverageSpending(averageSpending);
            categoryObj.setSpendingTrend(spendingTrend);
            // You can also calculate and update other statistics, such as spending trends over time, as needed
        }
    }

    class Category {
        private String category;
        private BigDecimal totalSpending;
        private BigDecimal averageSpending;
        private BigDecimal spendingTrend;
    
        public Category(String category) {
            this.category = category;
        }
    
        public String getCategory() {
            return category;
        }
    
        public BigDecimal getTotalSpending() {
            return totalSpending;
        }
    
        public void setTotalSpending(BigDecimal totalSpending) {
            this.totalSpending = totalSpending;
        }
    
        public BigDecimal getAverageSpending() {
            return averageSpending;
        }
    
        public void setAverageSpending(BigDecimal averageSpending) {
            this.averageSpending = averageSpending;
        }
        public BigDecimal getSpendingTrend() {
            return spendingTrend;
        }
    
        public void setSpendingTrend(BigDecimal spendingTrend) {
            this.spendingTrend = spendingTrend;
        }  
    }

        private void generateSpendingReports(Map<String, List<Transaction>> categories) {
            // Generate spending reports or summaries based on the categorized and analyzed transactions
        
            // Loop through each category
            for (Map.Entry<String, List<Transaction>> entry : categories.entrySet()) {
                String category = entry.getKey();
                List<Transaction> transactions = entry.getValue();
        
                // Print category name and total spending
                System.out.println("Category: " + category);
                System.out.println("Total spending: $" + String.format("%.2f", getCategoryTotalSpending(category, categories)));
        
                // Print average spending
                System.out.println("Average spending: $" + String.format("%.2f", getCategoryAverageSpending(category, categories)));
        
                // Print spending trend
                System.out.println("Spending trend: " + getCategorySpendingTrend(category, categories).multiply(BigDecimal.valueOf(100)).setScale(2) + "%");

        
                // You can also include other spending statistics or generate more detailed reports as needed
                System.out.println("Transactions: " + transactions.size());
                System.out.println("-------------------------------");
            }
        }
        
            private BigDecimal getCategoryTotalSpending(String category, Map<String, List<Transaction>> categories) {
                // Get total spending for the given category
                BigDecimal totalSpending = BigDecimal.ZERO; // Initialize to zero
                List<Transaction> transactions = categories.get(category);
                if (transactions != null) { // Check if the list is not null
                    for (Transaction transaction : transactions) {
                        totalSpending = totalSpending.add(transaction.getAmount()); // Use BigDecimal's add() method
                    }
                }
                return totalSpending;
            }
        
            private BigDecimal getCategoryAverageSpending(String category, Map<String, List<Transaction>> categories) {
                // Get average spending for the given category
                BigDecimal averageSpending = BigDecimal.ZERO; // Initialize to zero
                List<Transaction> transactions = categories.get(category);
                if (transactions != null && !transactions.isEmpty()) { // Check if the list is not null and not empty
                    BigDecimal totalSpending = getCategoryTotalSpending(category, categories); // Get total spending as BigDecimal
                    BigDecimal transactionCount = new BigDecimal(transactions.size()); // Convert transaction count to BigDecimal
        
                    // Use MathContext to specify desired precision and rounding mode
                    MathContext mathContext = new MathContext(2);
                    averageSpending = totalSpending.divide(transactionCount, mathContext); // Calculate average spending
                }
                return averageSpending; // Convert BigDecimal to double before returning
            }
        
            private BigDecimal getCategorySpendingTrend(String category, Map<String, List<Transaction>> categories) {
                // Get spending trend for the given category
                BigDecimal spendingTrend = BigDecimal.ZERO; // Initialize to zero
                List<Transaction> transactions = categories.get(category);
                if (transactions != null && transactions.size() >= 2) { // Check if the list is not null and has at least 2 transactions
                    BigDecimal firstTransactionAmount = transactions.get(0).getAmount();
                    BigDecimal lastTransactionAmount = transactions.get(transactions.size() - 1).getAmount();
        
                    // Calculate spending trend as percentage change (last - first) / first * 100
                    BigDecimal difference = lastTransactionAmount.subtract(firstTransactionAmount);
                    BigDecimal percentageChange = difference.divide(firstTransactionAmount, MathContext.DECIMAL128);
                    spendingTrend = percentageChange.multiply(new BigDecimal("100"));
                }
                return spendingTrend;
            }
    }


    
