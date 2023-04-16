package com.simplilearn.mavenproject.service;

import com.simplilearn.mavenproject.parsing.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class TransactionDataTable extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;
    private String documentTitle;

    public TransactionDataTable(String documentTitle, Map<String, List<Transaction>> categories) {
        this.documentTitle = documentTitle;
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
     * Opens a bank statement file and displays the transactions in a table.
     * @throws IOException 
     */
    public void openBankStatement() throws IOException {
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
            TransactionParser parser = new TransactionParser(documentTitle);
            Map<String, List<Transaction>> categories = null;
            try {
                categories = parser.getTransactions(selectedFile);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Error parsing bank statement file.");
                e.printStackTrace();
                return;
            }
            }
        }

    private boolean isValidBankStatement(File file) {
        // Add your validation logic here
        return true;
    }
}
