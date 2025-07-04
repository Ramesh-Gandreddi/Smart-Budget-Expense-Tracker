package services;

import java.util.List;
import models.Budget;
import models.Transaction;
import models.User;

public class TransactionService {
    private User user;
    private Budget budget;
    private List<Transaction> transactions;

    public TransactionService(User user, Budget budget) {
        this.user = user;
        this.budget = budget;
        this.transactions = FileManager.loadTransactions(); // Load from file initially
    }

    public void addTranction(Transaction transaction) {
        user.addTranction(transaction);         // Add to user
        transactions.add(transaction);          // Add to local list
        FileManager.saveTransactions(transactions); // Save updated list to file
    }

    public double getTotalIncome() {
        return user.getTotalIncome();
    }

    public double getTotalExpense() {
        return user.getTotalExpense();
    }

    public double getRemainingBudget() {
        return budget.getRemainingBudget(getTotalExpense());
    }

    public boolean isOverBudget() {
        return budget.isOverBudget(getTotalExpense());
    }

    public List<Transaction> getAllTranctions() {
        return user.getTransactions();
    }
}
