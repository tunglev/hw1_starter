import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;


/**
 * The ExpenseTrackerApp class allows users to add/remove daily transactions.
 */
public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    DefaultTableModel tableModel = new DefaultTableModel();
    tableModel.addColumn("Serial");
    tableModel.addColumn("Amount");
    tableModel.addColumn("Category");
    tableModel.addColumn("Date");
    

    
    ExpenseTrackerView view = new ExpenseTrackerView(tableModel);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      
      // Get transaction data from view
      double amount = view.getAmount(); 
      String category = view.getCategory();

      // Create transaction object
      Transaction t = new Transaction(amount, category);

      // Call controller to add transaction
      view.addTransaction(t);
    });
    
    // Handle "Delete" menu item clicks
    view.getDeleteMenuItem().addActionListener(e -> {
    	view.delete();
    });
    
    // Handle "Open File..." menu item clicks
    view.getOpenFileMenuItem().addActionListener(e -> {
      String inputFileName = view.showFileChooser(true);
      if (inputFileName != null) {
        try {	
    	  BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
  	    
    	  int transactionCount = view.getTransactions().size();
    	  for (int i = 0; i < transactionCount; i++) {
    	    view.removeTransaction(0);
    	  }

    	  String currentLine = null;
    	  int currentLineNumber = 1;
    	  while ((currentLine = reader.readLine()) != null) {
    		// Skip the headers
    		if (currentLineNumber > 1) {
    		  StringTokenizer tokenizer = new StringTokenizer(currentLine, ",");
    		  String currentAmountString = tokenizer.nextToken();
    		  double currentAmount = Double.parseDouble(currentAmountString);
    		  String currentCategory = tokenizer.nextToken();
    		  String currentDate = tokenizer.nextToken();
				
    		  view.addTransaction(new Transaction(currentAmount, currentCategory, currentDate));
    		}
     		currentLineNumber++;
    	  }
    	  
    	  reader.close();
        }
        catch (IOException ioe) {
          view.displayErrorMessage(ioe.getMessage());
        }
      }
    });
    
    // Handle "Save" menu item clicks
    view.getSaveAsMenuItem().addActionListener(e -> {	  
  	  String outputFileName = view.showFileChooser(false);
  	  if (outputFileName != null) {
  		  CSVExporter csvExporter = new CSVExporter();
  		  String errorMessage = csvExporter.exportTransactions(view.getTransactions(), outputFileName);
  		  if (errorMessage != null) {
  			  view.displayErrorMessage(errorMessage);
  		  }
  	  }
    });

  }

}