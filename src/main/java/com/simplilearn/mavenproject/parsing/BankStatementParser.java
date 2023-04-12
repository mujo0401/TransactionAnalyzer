package com.simplilearn.mavenproject.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;


import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.simplilearn.mavenproject.domain.Transaction;
import com.simplilearn.mavenproject.service.TransactionExtractor;
import com.simplilearn.mavenproject.service.TransactionCategorizer;



public class BankStatementParser {

    public Map<String, List<Transaction>> parse(File file) throws IOException {
        List<Transaction> transactions;

        try (InputStream is = new FileInputStream(file)) {
            PDFParser parser = new PDFParser(is);
            parser.parse();
            PDDocument pdfDocument = parser.getPDDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String document = pdfStripper.getText(pdfDocument);

            // Extract transactions from text
            TransactionExtractor extractor = new TransactionExtractor(document);
            transactions = extractor.extractTransactions(null);

            // Categorize transactions
            TransactionCategorizer categorizer = new TransactionCategorizer(transactions);
            Map<String, List<Transaction>> categorizedTransactions = categorizer.categorizeTransactions();

            pdfDocument.close(); // Close the document after use
            return categorizedTransactions;
        } catch (IOException e) {
            throw new IOException("Error reading file", e);
        }
    }
}





