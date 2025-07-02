package models;

import java.time.LocalDate;

public class Income extends Transaction {
    private String source;  // Optional: e.g., "Salary", "Freelancing"

    public Income(double amount, LocalDate date, String source){
        super(amount,date);
        this.source = source;
    }
    public String getSource(){
        return source;
    }

    @Override
    public String getType(){
        return "income";
    }
}
