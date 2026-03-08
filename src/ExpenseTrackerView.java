

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List; 

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JMenuItem fileOpenFileMenuItem;
  private JMenuItem fileSaveAsMenuItem;
  private JMenuItem editDeleteMenuItem;
  private JTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private ExpenseTrackerModel expenseModel;

  

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
  
  public JMenuItem getOpenFileMenuItem() {
	  return fileOpenFileMenuItem;
  }
  
  public JMenuItem getSaveAsMenuItem() {
	  return fileSaveAsMenuItem;
  }
  
  public JMenuItem getDeleteMenuItem() {
	  return editDeleteMenuItem;
  }

  public DefaultTableModel getTableModel() {
    return model;
  }

  public ExpenseTrackerView(DefaultTableModel model, ExpenseTrackerModel expenseModel) {
    setTitle("Expense Tracker"); // Set title
 
    this.model = model;
    this.expenseModel = expenseModel;
    
    // Create the top menu bar
    JMenuBar topMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    topMenuBar.add(fileMenu);
    this.fileOpenFileMenuItem = new JMenuItem("Open File...");
    fileMenu.add(this.fileOpenFileMenuItem);
    this.fileSaveAsMenuItem = new JMenuItem("Save As...");
    fileMenu.add(this.fileSaveAsMenuItem);
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
  
  public String showFileChooser(boolean isOpenFile) {
	JFileChooser chooser = new JFileChooser();
	int result = -1;
	if (isOpenFile) {
		result = chooser.showOpenDialog(this);
	}
	else {
		result = chooser.showSaveDialog(this);
	}
	if (result == JFileChooser.APPROVE_OPTION) {
	  String path = chooser.getSelectedFile().getAbsolutePath();
	  if (!path.toLowerCase().endsWith(".csv")) {
	    path = path + ".csv";
	  }
	  return path;
	}
	return null;
  }
  
  public int getSelectedTransactionIndex() {
    return this.transactionsTable.getSelectedRow();
  }
  
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void refreshTable() {
    model.setRowCount(0);
    int rowNum = model.getRowCount();

    List<Transaction> transactions = expenseModel.getTransactions();
    for(Transaction t : transactions) {
      model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()});

    }
    Object[] totalRow = {"Total", null, null, expenseModel.computeTransactionsTotalCost()};
    model.addRow(totalRow);
  
    // Fire table update
    transactionsTable.updateUI();
  
  }  

  public void refresh() {
    refreshTable();
  }


  // Other view methods
}
