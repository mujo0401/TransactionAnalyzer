package com.simplilearn.mavenproject.service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateTransactionsCSV {

    public static void main(String[] args) {
        String fileName = "transactions.csv"; // File name
        String data = "TransactionID,Description,Amount\n" // CSV header
                + "1,Transaction 1,100.00\n" // Sample data
                + "2,Transaction 2,50.00\n" // Sample data
                + "3,Transaction 3,200.00"; // Sample data

        try {
            // Create FileWriter and BufferedWriter
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write data to the file
            bufferedWriter.write(data);

            // Close the BufferedWriter
            bufferedWriter.close();

            System.out.println("Successfully created transactions.csv file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}