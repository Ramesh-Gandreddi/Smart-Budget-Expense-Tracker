import java.time.LocalDate;
import java.util.Scanner;

import models.Budget;
import models.ExpenseTransaction;
import models.IncomeTransaction;
import models.Transaction;
import models.User;
import services.TransactionService;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create user and budget
        User user = new User("Ramesh");
        Budget budget = new Budget(10000); // You can modify this as needed
        TransactionService service = new TransactionService(user, budget);

        boolean running = true;

        while (running) {
            System.out.println("\n==== Smart Budget Expense Tracker ====");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Total Income");
            System.out.println("4. View Total Expense");
            System.out.println("5. View Remaining Budget");
            System.out.println("6. View All Transactions");
            System.out.println("7. Check Over Budget");
            System.out.println("8. Exit");
            System.out.print("Select option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Enter income source: ");
                    String source = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter category (e.g., Salary, Freelance): ");
                    String category = sc.nextLine();

                    Transaction income = new IncomeTransaction(amount, LocalDate.now(), category);
                    service.addTranction(income);
                    System.out.println("✅ Income added successfully!");
                    break;
                }

                case 2: {
                    System.out.print("Enter expense amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter expense category (e.g., Food, Rent): ");
                    String category = sc.nextLine();

                    Transaction expense = new ExpenseTransaction(amount, LocalDate.now(), category);
                    service.addTranction(expense);
                    System.out.println("✅ Expense recorded successfully!");
                    break;
                }

                case 3: {
                    double totalIncome = service.getTotalIncome();
                    System.out.println("💰 Total Income: ₹" + totalIncome);
                    break;
                }

                case 4: {
                    double totalExpense = service.getTotalExpense();
                    System.out.println("💸 Total Expense: ₹" + totalExpense);
                    break;
                }

                case 5: {
                    double balance = service.getTotalIncome() - service.getTotalExpense();
                    System.out.println("💼 Current Balance: ₹" + balance);
                    break;
                }

                case 6: {
                    System.out.println("📋 All Transactions:");
                    service.getAllTranctions(); // Make sure this prints all properly
                    break;
                }

                case 7: {
                    double income = service.getTotalIncome();
                    double expense = service.getTotalExpense();
                    if (expense > income) {
                        System.out.println("⚠️ Over Budget by ₹" + (expense - income));
                    } else {
                        System.out.println("✅ You are within budget.");
                    }
                    break;
                }

                case 8: {
                    running = false;
                    System.out.println("👋 Exiting... Thank you for using Smart Budget Expense Tracker!");
                    break;
                }

                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }

        sc.close();
    }
}

