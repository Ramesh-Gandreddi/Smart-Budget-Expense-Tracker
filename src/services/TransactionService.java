package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Budget;
import models.ExpenseTransaction;
import models.IncomeTransaction;
import models.Transaction;
import models.User;
import static services.DatabaseConfig.*;

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
        double totalIncome = 0.0;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT SUM(amount) FROM transactions WHERE type = 'INCOME'";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    totalIncome = rs.getDouble(1); // column 1: SUM(amount)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalIncome;
    }

    public double getTotalExpense() {
        double totalExpense = 0.0;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT SUM(amount) FROM transactions WHERE type = 'EXPENSE'";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    totalExpense = rs.getDouble(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalExpense;

    }

    public double getRemainingBudget() {
        return budget.getRemainingBudget(getTotalExpense());
    }

    public boolean isOverBudget() {
        return budget.isOverBudget(getTotalExpense());
    }

    public List<Transaction> listAllTransactionsFromDB() {
        List<Transaction> dbTransactions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT amount, date, category, type FROM transactions";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    double amount = rs.getDouble("amount");
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    String category = rs.getString("category");
                    String type = rs.getString("type");

                    Transaction t;
                    if ("INCOME".equalsIgnoreCase(type)) {
                        t = new IncomeTransaction(amount, date, category);
                    } else {
                        t = new ExpenseTransaction(amount, date, category);
                    }
                    dbTransactions.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbTransactions;
    }

    public List<Transaction> getTransactionsForCurrentMonth() {
        LocalDate now = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getDate().getMonth() == now.getMonth() && t.getDate().getYear() == now.getYear())
                .toList();
    }

    public void resetAllData() {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM transactions";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        transactions.clear();
        user.clearTransactions();
        System.out.println("All data has been deleted from database.");
    }

    public double getMonthlyIncome() {
        double total = 0.0;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT SUM(amount) FROM transactions WHERE type = 'INCOME' AND MONTH(date) = MONTH(CURDATE()) AND YEAR(date) = YEAR(CURDATE())";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                var rs = stmt.executeQuery();
                if (rs.next())
                    total = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public double getMonthlyExpense() {
        double total = 0.0;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT SUM(amount) FROM transactions WHERE type = 'EXPENSE' AND MONTH(date) = MONTH(CURDATE()) AND YEAR(date) = YEAR(CURDATE())";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                var rs = stmt.executeQuery();
                if (rs.next())
                    total = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Transaction> filterTransactionsByCategory(String category) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE category = ?";

        try (Connection conn =  DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(createTransactionFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<Transaction> filterTransactionsByDateRange(LocalDate from, LocalDate to) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE date >= ? AND date <= ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(createTransactionFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
    private Transaction createTransactionFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    double amount = rs.getDouble("amount");
    LocalDate date = rs.getDate("date").toLocalDate();
    String category = rs.getString("category");
    String type = rs.getString("type");

    if ("income".equalsIgnoreCase(type)) {
        return new IncomeTransaction(amount, date, category);
    } else {
        return new ExpenseTransaction(amount, date, category);
    }
}


}
