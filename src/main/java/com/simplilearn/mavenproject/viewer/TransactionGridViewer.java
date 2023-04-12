package com.simplilearn.mavenproject.viewer;

import com.simplilearn.mavenproject.domain.Transaction;
import com.simplilearn.mavenproject.parsing.TransactionParser;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;


import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;





public class TransactionGridViewer extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;

    public TransactionGridViewer(Map<String, List<Transaction>> categories) {
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
     * 
     */
    public void openBankStatement() {
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

            //Load PDF document
            TransactionParser parser = new TransactionParser();
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