package com.simplilearn.mavenproject.extractor;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import com.simplilearn.mavenproject.service.Transaction;
import org.apache.pdfbox.text.PDFTextStripper;

public class TransactionExtractor {
    private static final String[] DATE_FORMATS = null;
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

    public List<Transaction> extractTransactions(PDDocument pdfDocument) throws ParseException {
        List<Transaction> transactions = new ArrayList<>();

        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            PDPageTree pages = pdfDocument.getPages(); // Get the PDPageTree from the PDDocument
            for (PDPage page : pages) { // Iterate over PDPage objects
                // Extract text from the PDPage object
                textStripper.processPage(page); // Process the page using PDFTextStripper
               
                // Parse the pageText to extract transactions and create Transaction objects
                // based on the extracted information
                // Assuming you have a method called parseTransactions that takes pageText as input
                parseTransactionLine(null, null, null); // Call the parseTransactionLine method with appropriate arguments
            }
        } catch (IOException e) {
            // Handle IOException
        }

        return transactions;
    }
    
    public List<Transaction> parseTransactions(String pageText) throws ParseException {
        List<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat dateFormat;
        Date date = null;
        String description = null;
        BigDecimal amount = new BigDecimal(0.0);

        // Try parsing with each date format
        for (String format : DATE_FORMATS) {
            dateFormat = new SimpleDateFormat(format);
            // Parse the pageText to extract transactions
            String[] lines = pageText.split("\\r?\\n");
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                try {
                    // Try parsing date from line using the current date format
                    date = dateFormat.parse(line.trim());
                } catch (ParseException e) {
                    // Ignore and continue if date cannot be parsed with current format
                }
                if (date != null) {
                    // If date is parsed, it means this line contains a transaction
                    // Extract description and amount from subsequent lines
                    for (int i = 1; i < lines.length; i++) {
                        String nextLine = lines[i].trim();
                        if (!nextLine.isEmpty()) {
                            if (amount.compareTo(BigDecimal.ZERO) == 0) {
                                // If amount is not yet extracted, try parsing it from current line
                                try {
                                    amount = new BigDecimal(nextLine);
                                } catch (NumberFormatException ex) {
                                    // Ignore and continue if amount cannot be parsed
                                }
                            } else {
                                // If description is not yet extracted, set it as current line
                                if (description == null) {
                                    description = nextLine;
                                } else {
                                    // If description is already extracted, it means this transaction is complete
                                    // Create Transaction object and add it to the list
                                    transactions.add(new Transaction(date, description, amount));
                                    // Reset date, description, and amount for next transaction
                                    date = null;
                                    description = null;
                                    amount = new BigDecimal(0.0);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // If transactions are found with current date format, no need to try other formats
            if (!transactions.isEmpty()) {
                break;
            }
        }

        return transactions;
    }
    public Transaction parseTransactionLine(String line, SimpleDateFormat dateFormat, BigDecimal amount)
            throws ParseException {
        Date date = dateFormat.parse(line.trim());
        return new Transaction(date, line, amount);       
    }
}
	