

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List; 

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JMenuItem editDeleteMenuItem;
  private JTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private List<Transaction> transactions = new ArrayList<>();

  

  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmount() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public String getCategory() {
    return categoryField.getText();
  }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  
  public JMenuItem getDeleteMenuItem() {
	  return editDeleteMenuItem;
  }

  public DefaultTableModel getTableModel() {
    return model;
  }

  public ExpenseTrackerView(DefaultTableModel model) {
    setTitle("Expense Tracker"); // Set title
 
    this.model = model;
    
    // Create the top menu bar
    JMenuBar topMenuBar = new JMenuBar();
    JMenu editMenu = new JMenu("Edit");
    topMenuBar.add(editMenu);
    this.editDeleteMenuItem = new JMenuItem("Delete");
    editMenu.add(editDeleteMenuItem);
    setJMenuBar(topMenuBar);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    amountField = new JTextField(10);
    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);
    transactionsTable = new JTable(model);
    transactionsTable.setDefaultEditor(Object.class, null);
    transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  
    // Layout components
    JPanel addTransactionPanel = new JPanel();
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
    addTransactionPanel.add(inputPanel);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
    addTransactionPanel.add(buttonPanel);
  
    // Add panels to frame
    add(addTransactionPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
  
    // Set frame properties
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }
  
  public void delete() {
    int selectedTransactionID = this.transactionsTable.getSelectedRow();
    // Perform input validation
    if ((selectedTransactionID < 0) || (selectedTransactionID > transactions.size() - 1)) {
	  displayErrorMessage("A transaction was not selected to be deleted.");
	}
	else {
	  removeTransaction(selectedTransactionID); 
	}
  }
  
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void refreshTable(List<Transaction> transactions) {
    // model.setRowCount(0);
    model.setRowCount(0);
    int rowNum = model.getRowCount();
  
    // Add rows from transactions list
    for(Transaction t : transactions) {
      model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()});

    }
    Object[] totalRow = {"Total", null, null, computeTransactionsTotalCost()};
    model.addRow(totalRow);
  
    // Fire table update
    transactionsTable.updateUI();
  
  }  

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = getTransactions();
  
    // Pass to view
    refreshTable(transactions);
  
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }
  
  public void addTransaction(Transaction t) {
    transactions.add(t);
    getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
  }
  
  public void removeTransaction(int transactionID) {
	  transactions.remove(transactionID);
	  refresh();
  }
  
  public double computeTransactionsTotalCost() {
    double totalCost=0;
    for(Transaction t : transactions) {
      totalCost+=t.getAmount();
    }
    return totalCost;
  }
  


  // Other view methods
}
