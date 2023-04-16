package com.simplilearn.mavenproject.viewer;

import com.simplilearn.mavenproject.service.Transaction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TransactionTableRenderer extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;

    public TransactionTableRenderer(Map<String, List<Transaction>> categories) {
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

    public void renderTransactions(Map<String, List<Transaction>> categories) {
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
