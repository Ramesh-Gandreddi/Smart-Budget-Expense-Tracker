package models;

import java.time.LocalDate;

public class Expense extends Transaction {
   private Category category;
   private String note; // Optional: e.g., "Pizza night", "Bus ticket"

   public Expense(double amount,LocalDate date, Category category,String note){
    super(amount,date);
    this.category = category;
    this.note = note;
   }

   public Category getCategory(){
    return category;
   }
   public String getNote(){
    return note;
   }

   @Override
   public String getType(){
    return "Expense";
   }
}
