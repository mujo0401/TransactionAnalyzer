package com.simplilearn.mavenproject.parsing;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.simplilearn.mavenproject.domain.Transaction;



public class TransactionParser {
    private static final String[] DATE_FORMATS = { "MM/dd/yyyy", "yyyy/MM/dd" };
    private static final String AMOUNT_REGEX = "[-+]?[0-9]*\\.?[0-9]+";
    private static final String[] AMOUNT_PREFIXES = { "$", "USD" };

    public List<Transaction> parse(String text) throws ParseException {
        List<Transaction> transactions = new ArrayList<>();

        // Split text into lines
        String[] lines = text.split("\\r?\\n");

        Transaction currentTransaction = null;
        for (String line : lines) {
            if (isStartOfTransaction(line)) {
                if (currentTransaction != null) {
                    transactions.add(currentTransaction);
                }
                BigDecimal amount = new BigDecimal("0.0");
                currentTransaction = new Transaction(null, line, amount);
            }
            if (currentTransaction != null) {
                parseTransactionLine(currentTransaction, line);
            }
        }
        if (currentTransaction != null) {
            transactions.add(currentTransaction);
        }

        return transactions;
    }

    private boolean isStartOfTransaction(String line) {
        for (String amountPrefix : AMOUNT_PREFIXES) {
            if (line.contains(amountPrefix)) {
                String amountSubstr = line.substring(line.indexOf(amountPrefix) + amountPrefix.length());
                if (amountSubstr.matches(AMOUNT_REGEX)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void parseTransactionLine(Transaction transaction, String line) throws ParseException {
        SimpleDateFormat dateFormat;
        Date date = null;
        String description = null;
        BigDecimal amount = new BigDecimal(0.0);

        // Try parsing with each date format
        for (String format : DATE_FORMATS) {
            dateFormat = new SimpleDateFormat(format);
            try {
                date = dateFormat.parse(line.substring(0, format.length()));
                description = line.substring(format.length(), line.indexOf("$")).trim();
                String amountStr = line.substring(line.indexOf("$") + 1);
                amount = new BigDecimal(amountStr.replaceAll(",", ""));
                break;
            } catch (ParseException | NumberFormatException e) {
                // Ignore and try next date format
            }
        }

        if (date == null) {
            throw new ParseException("Could not parse transaction date: " + line, 0);
        }
        if (description == null) {
            throw new ParseException("Could not parse transaction description: " + line, 0);
        }

        transaction.setDate(date);
        transaction.setDescription(description);
        transaction.setAmount(amount);
    }

    public Map<String, List<Transaction>> getTransactions(File selectedFile) {
        return null;
    }
}