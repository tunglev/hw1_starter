import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


//  controller handles input and connects model and view
public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    this.view.getAddTransactionBtn().addActionListener(e -> addTransaction());
    this.view.getDeleteMenuItem().addActionListener(e -> deleteTransaction());
    this.view.getOpenFileMenuItem().addActionListener(e -> openFile());
    this.view.getSaveAsMenuItem().addActionListener(e -> saveFile());
  }

  private void addTransaction() {
    double amount = view.getAmount();
    String category = view.getCategory();

    Transaction t = new Transaction(amount, category);

    model.addTransaction(t);
    view.refresh();
  }


  private void deleteTransaction() {
    int selectedTransactionID = view.getSelectedTransactionIndex();
    
    if ((selectedTransactionID < 0) || (selectedTransactionID > model.getTransactionCount() - 1)) {
      view.displayErrorMessage("A transaction was not selected to be deleted.");
    } else {
      model.removeTransaction(selectedTransactionID);
      view.refresh();
    }
  }

  // csv
  private void openFile() {
    String inputFileName = view.showFileChooser(true);
    if (inputFileName != null) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        
        // clear all current transaction
        int transactionCount = model.getTransactionCount();
        for (int i = 0; i < transactionCount; i++) {
          model.removeTransaction(0);
        }

        String currentLine = null;
        int currentLineNumber = 1;
        while ((currentLine = reader.readLine()) != null) {
          // ignore first line since it is the header
          if (currentLineNumber > 1) {
            StringTokenizer tokenizer = new StringTokenizer(currentLine, ",");
            String currentAmountString = tokenizer.nextToken();
            double currentAmount = Double.parseDouble(currentAmountString);
            String currentCategory = tokenizer.nextToken();
            String currentDate = tokenizer.nextToken();
            
            model.addTransaction(new Transaction(currentAmount, currentCategory, currentDate));
          }
          currentLineNumber++;
        }
        
        reader.close();
        
        view.refresh();
      } catch (IOException ioe) {
        view.displayErrorMessage(ioe.getMessage());
      }
    }
  }
  
  private void saveFile() {
    String outputFileName = view.showFileChooser(false);
    if (outputFileName != null) {
      CSVExporter csvExporter = new CSVExporter();
      String errorMessage = csvExporter.exportTransactions(model.getTransactions(), outputFileName);
      if (errorMessage != null) {
        view.displayErrorMessage(errorMessage);
      }
    }
  }

  
  public void refresh() {
    view.refresh();
  }
}
