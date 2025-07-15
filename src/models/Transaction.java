package models;

import java.time.LocalDate;

public abstract class Transaction {
    protected double amount;
    protected LocalDate date;
    protected int id;

    public Transaction(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
    

    public abstract String getCategory();

    public abstract String getType(); // "Income" or "Expense"

  //  protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | %.2f | %s", id, date, amount, getCategory());
    }

}
