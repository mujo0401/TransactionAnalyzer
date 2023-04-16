package com.simplilearn.mavenproject.controllers;

import com.simplilearn.mavenproject.parsing.TransactionParser;
import com.simplilearn.mavenproject.service.Transaction;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class TransactionTableController extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;

    public TransactionTableController(Map<String, List<Transaction>> categories) {
        setTitle("Transaction Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        model = new DefaultTableModel(new Object[]{"Category", "Account Number", "Date", "Description", "Amount", "Editable"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        for (Map.Entry<String, List<Transaction>> entry : categories.entrySet()) {
            for (Transaction transaction : entry.getValue()) {
                model.addRow(new Object[]{entry.getKey(), transaction.getAccountNumber(), transaction.getDate(),
                        transaction.getDescription(), transaction.getAmount(), false});
            }
        }
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();
    }

    /**
     * Open a bank statement file and display transactions in a grid.
     * @throws ParseException 
     * @throws IOException 
     */
    public void openBankStatement() throws IOException, ParseException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF files", "pdf")); // Set file filter to only allow PDF files
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Validate file
            if (!isValidBankStatement(selectedFile)) {
                JOptionPane.showMessageDialog(this, "Invalid bank statement file selected.");
                return;
            }
            // Load PDF document
            TransactionParser parser = new TransactionParser(selectedFile);
            Map<String, List<Transaction>> categories = parser.getTransactions(selectedFile);

            // Display transactions in a grid
            setTitle("Transaction Viewer");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(800, 600));

            model = new DefaultTableModel(new Object[]{"Category", "Account Number", "Date", "Description", "Amount", "Editable"}, 0);
            table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            for (Map.Entry<String, List<Transaction>> entry : categories.entrySet()) {
                for (Transaction transaction : entry.getValue()) {
                    model.addRow(new Object[]{entry.getKey(), transaction.getAccountNumber(), transaction.getDate(),
                            transaction.getDescription(), transaction.getAmount(), false});
                }
            }
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            pack();
        }
    }

    private boolean isValidBankStatement(File file) {
        // Add your validation logic here
        return true;
    }
}
