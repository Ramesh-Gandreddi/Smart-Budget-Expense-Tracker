package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import models.Budget;
import models.IncomeTransaction;
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

        for (Transaction transaction : transactions) {
            user.addTranction(transaction);
        }
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction); // still stores in memory for CLI use

        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO transactions (amount, category, date, type) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, transaction.getAmount());
                stmt.setString(2, transaction.getCategory());
                stmt.setString(3, transaction.getDate().toString());
                stmt.setString(4, transaction instanceof IncomeTransaction ? "INCOME" : "EXPENSE");
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<Transaction> getTransactionsForCurrentMonth() {
        LocalDate now = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getDate().getMonth() == now.getMonth() && t.getDate().getYear() == now.getYear())
                .toList();
    }

    public void resetAllData() {
        transactions.clear();
        user.getTransactions().clear();
        FileManager.saveTransactions(transactions);
        System.out.println("All data has been reset.");
    }

}
