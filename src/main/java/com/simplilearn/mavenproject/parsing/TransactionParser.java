package com.simplilearn.mavenproject.parsing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import java.util.HashMap;

import com.simplilearn.mavenproject.service.Transaction;

public class TransactionParser {
	private String documentTitle;
	private final Map<String, String> categories;
    private final TransactionParser parser;

    public TransactionParser(String title) {
       {
            documentTitle = title;
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
                String pageText = textStripper.getText(pdfDocument);
               
                // Parse the pageText to extract transactions and create Transaction objects
                // based on the extracted information
                List<Transaction> parsedTransactions = parser.parse(pageText);
                transactions.addAll(parsedTransactions);
            }
        } catch (IOException e) {
            // Handle IOException
        }

        return transactions;
    }
    
    public List<Transaction> parse(String text) throws ParseException {
        return parser.parse(text);
    }

    public Map<String, List<Transaction>> getTransactions(File selectedFile) throws IOException, ParseException {
        String text = new String(Files.readAllBytes(selectedFile.toPath()));
        List<Transaction> transactions = parser.parse(text);
        return categorizeTransactions(transactions);
    }
}

