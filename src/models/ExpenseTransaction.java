package models;

import java.time.LocalDate;

public class ExpenseTransaction extends Transaction {
    private String category;

    public ExpenseTransaction(double amount, LocalDate date, String category) {
        super(amount, date);
        this.category = category;
    }

    @Override
    public String getType() {
        return "Expense";
    }
@Override
    public String getCategory() {
        return category;
    }
   @Override
public String toString() {
    return String.format("[Expense] ID: %d | %s | %.2f | %s", getId(), getDate(), getAmount(), getCategory());
}

}

