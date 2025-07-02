package models;

public class Budget {
  private double monthlyLimit;

  public Budget(double monthlyLimit){
    this.monthlyLimit = monthlyLimit;
  }

  public double getMonthlyLimit(){
    return monthlyLimit;
  }
  public void setMonthlyLimit(double monthlyLimit){
    this.monthlyLimit = monthlyLimit;
  }
  public boolean isOverBudget(double totalExpense){
         return totalExpense > monthlyLimit;
  }
  public double getRemainingBudget(double totalExpenses){
        return monthlyLimit-totalExpenses;
  }
}