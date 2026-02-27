import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseTrackerTest {

  private ExpenseTrackerView view;
  private ExpenseTrackerApp app;

  @BeforeEach
  public void setup() {
    DefaultTableModel tableModel = new DefaultTableModel();
    tableModel.addColumn("Serial");
    tableModel.addColumn("Amount");
    tableModel.addColumn("Category");
    tableModel.addColumn("Date");
    view = new ExpenseTrackerView(tableModel);
    app = new ExpenseTrackerApp();
  }
  
  @Test
  public void testInitialConfiguration() {
    // There aren't any pre-conditions to be checked
    // The setup method called the constructors
    // Check the post-conditions
    assertEquals(0, view.getTransactions().size());
  }

  private void testAddTransactionHelper(double amount, String category) {
	    // Check the pre-conditions
	    assertEquals(0, view.getTransactions().size());
		
	    // Create a new transaction and add it
	    Transaction transaction = new Transaction(amount, category);
	    view.addTransaction(transaction);

	    // Check the post-conditions: 
	    // Verify that the transaction was added appropriately
	    java.util.List<Transaction> transactions = view.getTransactions();
	    assertEquals(1, transactions.size());
	    assertEquals(amount, transactions.get(0).getAmount(), 0.001);
	    assertEquals(category, transactions.get(0).getCategory());
	    assertEquals(amount, view.computeTransactionsTotalCost(), 0.001);
  }
  
  @Test
  public void testAddTransaction() {
	  double amount = 100.0;
	  String category = "Food";
	  this.testAddTransactionHelper(amount, category);
  }
  
  @Test
  public void testRemoveTransaction() {
	  // Initialize: Add a new transaction
	  double amount = 100.0;
	  String category = "Food";
	  this.testAddTransactionHelper(amount, category);
	  // Remove that transaction
	  view.removeTransaction(0);
	  // Check the post-conditions
	  assertEquals(0, view.getTransactions().size());
	  assertEquals(0, view.computeTransactionsTotalCost(), 0.001);
  }
}