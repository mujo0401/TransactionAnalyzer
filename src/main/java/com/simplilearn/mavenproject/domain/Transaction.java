package com.simplilearn.mavenproject.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String accountNumber;
    private Date date;
    private String description;
    private BigDecimal amount;
    private String category;
    

    public Transaction(String accountNumber, Date date, String description, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.date = date;
        this.description = description;
        this.amount = amount;
        
    }

    public Transaction(Date date, String description, BigDecimal amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %.2f", accountNumber, date, description, amount);
    }
}