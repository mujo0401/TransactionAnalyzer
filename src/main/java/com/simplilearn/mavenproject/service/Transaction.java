package com.simplilearn.mavenproject.service;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private String accountNumber;
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private String category;
    
    public Transaction(LocalDate date, String description, BigDecimal amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;       
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
    
    public LocalDate getDate() {
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
    	return "date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
   
}