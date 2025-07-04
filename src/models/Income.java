package models;

import java.time.LocalDate;

public class Income {
    private String source;
    private double amount;
    private String category;

    public Income(String source, double amount, String category) {
        this.source = source;
        this.amount = amount;
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}

