# 💰 Smart Budget Expense Tracker

A simple yet powerful command-line-based Java application that helps users manage their income, expenses, and budgets efficiently.

## 🚀 Features

- ✅ Add income with category and amount
- ✅ Add expense with category and amount
- ✅ View total income and expenses
- ✅ Calculate remaining budget
- ✅ Check if you're over budget
- ✅ View all transactions
- 💾 Persist data using local file storage (`data.txt`)

## 🛠️ Technologies Used

- Java (OOP, Abstraction, Encapsulation, Polymorphism)
- Java Collections (ArrayList, List)
- File I/O for data persistence
- Modular structure using `models`, `services`

## 📁 Project Structure

smart-budget-expense-tracker/
│
├── models/
│ ├── User.java
│ ├── Budget.java
│ ├── Transaction.java
│ ├── IncomeTransaction.java
│ └── ExpenseTransaction.java
│
├── services/
│ ├── TransactionService.java
│ └── FileManager.java
│
├── data.txt // File to store transaction data
└── Main.java // Entry point

bash
Copy
Edit

## 🧑‍💻 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/Ramesh-Gandreddi/Smart-Budget-Expense-Tracker.git
   cd Smart-Budget-Expense-Tracker
Compile the project

bash
Copy
Edit
javac Main.java models/*.java services/*.java
Run the application

bash
Copy
Edit
java Main
Start tracking your budget!

📦 Sample Demo (Console UI)
sql
Copy
Edit
 Smart Budget Expense Tracker
1. Add Income
2. Add Expense
3. View Total Income
4. View Total Expense
5. View Remaining Budget
6. Check Over Budget
7. View All Transactions
8. Exit
Select option: 
📌 Future Enhancements
GUI-based interface (JavaFX or Spring Boot Web)

Category-wise analytics

User authentication

Export reports to PDF/CSV

🧠 Learning Outcome
This project demonstrates practical use of:

Low-Level Design principles

Clean code structure with OOP

Modular Java application with file persistence

Real-world console-based user interaction

📝 Author
Ramesh Gandreddi
🔗 GitHub Profile