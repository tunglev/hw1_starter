import java.util.ArrayList;
import java.util.List;

// handle data model for the expense tracker
public class ExpenseTrackerModel {

  private List<Transaction> transactions;

  public ExpenseTrackerModel() {
    this.transactions = new ArrayList<>();
  }

  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  public void removeTransaction(int transactionID) {
    if (transactionID >= 0 && transactionID < transactions.size()) {
      transactions.remove(transactionID);
    }
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public double computeTransactionsTotalCost() {
    double totalCost = 0;
    for (Transaction t : transactions) {
      totalCost += t.getAmount();
    }
    return totalCost;
  }

  public int getTransactionCount() {
    return transactions.size();
  }
}
