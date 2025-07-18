package models;

import java.time.LocalDate;

public class IncomeTransaction extends Transaction {
    private String category;

    public IncomeTransaction(double amount, LocalDate date,String category ) {
        super(amount, date);
        this.category = category;
    }

    @Override
    public String getType() {
        return "Income";
    }

    public String getCategory() {
        return category;
    }
   @Override
public String toString() {
    return String.format("[Income] ID: %d | %s | %.2f | %s", getId(), getDate(), getAmount(), getCategory());
}

}

