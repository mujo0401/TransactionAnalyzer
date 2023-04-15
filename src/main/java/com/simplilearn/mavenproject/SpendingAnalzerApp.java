package com.simplilearn.mavenproject;

import com.simplilearn.mavenproject.service.Transaction;
import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import com.simplilearn.mavenproject.service.TransactionDataLoader;

public class SpendingAnalzerApp {
    private TransactionDataLoader dataLoader;


    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JTextArea textArea;


    public SpendingAnalzerApp() {
        dataLoader = new TransactionDataLoader();
  

        frame = new JFrame("Spending Analyzer App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label = new JLabel("Enter a category to view transactions (Grocery, Utilities, Medical, , Other):");
        textField = new JTextField(20);
        JButton button = new JButton("View Transactions");
        JButton selectFileButton = new JButton("Select Bank Statement File");
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        frame.setLayout(new BorderLayout()); // Use BorderLayout for modern layout
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY); // Set background color
        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(button);
        topPanel.add(selectFileButton); // Add the "Load PDF" button
        frame.add(topPanel, BorderLayout.NORTH); // Add top panel to the north (top) of the frame
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER); // Add scrollable text area to the center of the frame
        frame.pack();
        frame.setVisible(true);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY); // Set background color
        bottomPanel.add(selectFileButton);
        frame.add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel to the south (bottom) of the frame
        

        // Add an ActionListener to the selectFileButton
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    java.io.File file = fileChooser.getSelectedFile();
                    // Call a method to parse the selected file
                    parseBankStatementFile(file);
                }
            }
        });  
  
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = textField.getText().trim().toLowerCase();
                Map<String, List<Transaction>> categories;
                try {
                    categories = categorizeTransactions(dataLoader.getTransactions());
                    if (categories.containsKey(category)) {
                        List<Transaction> categoryTransactions = categories.get(category);
                        textArea.setText("Transactions in " + category + " category:\n");
                        for (Transaction transaction : categoryTransactions) {
                            textArea.append(transaction.toString() + "\n");
                        }
                    } else {
                        textArea.setText("No transactions found in " + category + " category.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle the exception appropriately, e.g., show an error message to the user
                }
            }
        });

     
    }

    private void parseBankStatementFile(java.io.File file) {
        try {
        	
            // Create a new CMapParser object
            // Use PDFBox to extract the text from the PDF file
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            List<Transaction> transactions = new ArrayList<>();

            // Split the text into lines and process each line
            String[] lines = text.split("3/6,\"Sage,\"1969.99");
            for (String line : lines) {
                // Split the line into fields using a regular expression that matches the date format
            	String[] fields = line.split(",");
                if (fields.length == 3) {
                    // Parse the transaction data from the fields
                    String description = fields[0].trim();
                    String amountStr = fields[1].trim();
                    BigDecimal amount = new BigDecimal(amountStr);
                    String[] dateFields = line.split("\\s+");
                    LocalDate date = LocalDate.parse(dateFields[0], DateTimeFormatter.ofPattern("M/d"));
                    // Create a new Transaction object with the parsed date and add it to the list of transactions
                    // Create a new Transaction object and add it to the list of transactions
                    Transaction transaction = new Transaction(date, description, amount);
                    transactions.add(transaction);
                } else {
                    System.out.println("Invalid line: " + line);
                }
            }

            // Process the transactions as needed, e.g., update data structures, perform calculations, etc.
            // ...

            // Example: Print the parsed transactions to the console
            System.out.println("Parsed Transactions:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message to the user
        }
    }
 
    
    private Map<String, List<Transaction>> categorizeTransactions(List<Transaction> transactions) {
        Map<String, List<Transaction>> categorizedTransactions = new HashMap<>();

        for (Transaction transaction : transactions) {
            String category = getCategory(transaction);

            if (!categorizedTransactions.containsKey(category)) {
                categorizedTransactions.put(category, new ArrayList<>());
            }
            categorizedTransactions.get(category).add(transaction);
        }
        return categorizedTransactions;
    }

    private String getCategory(Transaction transaction) {
        String description = transaction.getDescription().toLowerCase();

        if (description.contains("Multifamily") || description.contains("ATT*")|| description.contains("Xcel Energy") || description.contains(" BDS LAUNDRY KIOSK*")) {
            return "Utilities";
        } else if (description.contains("PANERA BREAD") || description.contains(" FIVE GUYS ")) {
            return "Restaurant";
        } else if (description.contains("Cub Foods") || description.contains("LUNDS&BYERLYS")) {
            return "Grocery";
        } else if (description.contains("Espn Plus") || description.contains("Zwift, Inc.") || description.contains("Amazon Prime")|| description.contains("Patreon") || description.contains(" Audible") || description.contains("Sling TV") || description.contains("  Netflix")) {
            return "Streaming Services";
        } else if (description.contains("MAINSTREAM CBD") || description.contains("PANERA BREAD")) {
            return "Entertainment";
        } else if (description.contains("Old Republic Ttl Payroll")) {
            return "Income";
        } else if (description.contains("North Memorial") || description.contains("Clearbalance") || description.contains("MAYO CLINIC")) {
            return "Medical";
        } else if (description.contains("Sage")) {
            return "Rent";
        } else if (description.contains("Nissan Auto Loan")) {
            return "Auto";
        } else if (description.contains("FETCH") || description.contains("ALLSTATE") || description.contains("STATE FARM")) {
            return "Insurance";
        } else if (description.contains("Nissan Auto Loan")) {
            return "Loan";
        } else {
            return "Other";
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SpendingAnalzerApp();
            }
        });
    }
}