package models;

import java.time.LocalDate;

public abstract class Transaction {
    protected double amount;
    protected LocalDate date;

    public Transaction(double amount, LocalDate date){
        this.amount = amount;
        this.date = date;
    }

    public double getAmount(){
        return amount;
    }
    public LocalDate getDate(){
        return date;
    }
    public abstract String getType(); // "Income" or "Expense"
      public abstract String getCategory(); 
}
