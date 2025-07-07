package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final String userId;
    private String name;
    private List<Transaction> transactions;

    public User(String name) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.transactions = new ArrayList<>();

        // UUID = Universally Unique Identifier,
        // unique string (e.g., b3d6123a-22ab-4cda-b68c-72f47b7fd1e1) that is almost
        // impossible to duplicate.

    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTranction(Transaction t) {
        transactions.add(t);
    }

    public double getTotalIncome() {
        return transactions.stream().filter(t -> t.getType().equals("Income")).mapToDouble(t -> t.getAmount()).sum();
    }
    //// Transaction::getAmount is method reference, same as saying:.mapToDouble(t
    //// -> t.getAmount())
    // ➤ .sum() Adds up all the double values (i.e., all the income amounts).

    public double getTotalExpense() {
        return transactions.stream().filter(t -> t.getType().equals("Expense")).mapToDouble(Transaction::getAmount)
                .sum(); // above & this both are same
    }

    public void clearTransactions() {
        transactions.clear();
    }

}
