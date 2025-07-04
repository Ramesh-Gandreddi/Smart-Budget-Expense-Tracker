package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import models.ExpenseTransaction;
import models.IncomeTransaction;
import models.Transaction;

public class BudgetService {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTranction(Transaction t) {
        transactions.add(t);
        saveTransactionToFile(t); // 👈 Add this line
    }

    public double getTotalIncome() {
        return transactions.stream()
            .filter(t -> t.getType().equals("Income"))
            .mapToDouble(t -> t.getAmount())
            .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
            .filter(t -> t.getType().equals("Expense"))
            .mapToDouble(t -> t.getAmount())
            .sum();
    }

    public void saveTransactionToFile(Transaction t) {
        String fileName = "data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String data = t.getDate().toString() + "," 
                        + t.getType() + "," 
                        + t.getAmount() + ",";

            if (t instanceof IncomeTransaction) {
                data += ((IncomeTransaction)t).getCategory();
            } else if (t instanceof ExpenseTransaction) {
                data += ((ExpenseTransaction)t).getCategory();
            } else {
                data += "Unknown";
            }

            writer.write(data);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("❌ Error writing transaction to file: " + e.getMessage());
        }
    }
}

