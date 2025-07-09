package services;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.ExpenseTransaction;
import models.IncomeTransaction;
import models.Transaction;

public class FileManager {
    private static final String FILE_NAME ="data.txt";

    public static void saveTransactions(List<Transaction> transactions) {
    System.out.println("Saving transactions to file..."); // ✅ debug print

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (Transaction t : transactions) {
            writer.write(t.getType() + ", " + t.getAmount() + ", " + t.getDate() + ", " + t.getCategory());
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving transactions: " + e.getMessage());
    }
}


    public static List<Transaction> loadTransactions(){
        List<Transaction> transactions =new ArrayList<>(); 
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                   LocalDate date = LocalDate.parse(parts[2].trim());

                    String category = parts[3];

                    if (type.equals("Income")) {
                        transactions.add(new IncomeTransaction(amount, date, category));
                    } else if (type.equals("Expense")) {
                        transactions.add(new ExpenseTransaction(amount, date, category));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No saved transactions found.");
        }
        return transactions;
    }
    public static void clearTransactionFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        // Just overwrite with nothing
        writer.write("");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    }

