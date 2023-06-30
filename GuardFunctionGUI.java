import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;



class Visitor {
    private String name;
    private String phoneNumber;
    private String carPlateNumber;
    private String destination; 
    private Date timeIn;
    private Date timeOut;

    public Visitor(String name, String phoneNumber, String carPlateNumber, String destination) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carPlateNumber = carPlateNumber;
        this.destination = destination;
        this.timeIn = new Date();
    }

    public void setTimeOut() {
        this.timeOut = new Date();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }
    
    public String getDestination() {
        return destination;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }
}

class GuardFunction {
    private List<Visitor> visitors;

    public GuardFunction() {
        this.visitors = new ArrayList<>();
    }

    public void registerVisitor(String name, String phoneNumber, String carPlateNumber,  String destination) {
        Visitor visitor = new Visitor(name, phoneNumber, carPlateNumber, destination);
        visitors.add(visitor);
        System.out.println("Visitor " + name + " registered at " + visitor.getTimeIn() + " with car plate number: " + carPlateNumber + ", Destination: " + destination);
    }

    public void signOutVisitor(String name) {
        for (Visitor visitor : visitors) {
            if (visitor.getName().equalsIgnoreCase(name)) {
                visitor.setTimeOut();
                System.out.println("Visitor " + name + " signed out at " + visitor.getTimeOut());
                return;
            }
        }
        System.out.println("Visitor " + name + " not found.");
    }

    public void removeVisitor(String name) {
        visitors.removeIf(visitor -> visitor.getName().equalsIgnoreCase(name));
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }
}

public class GuardFunctionGUI extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField plateNumberField;
    private JTextField destinationField;
    private JTable visitorTable;
    private DefaultTableModel tableModel;
    private GuardFunction guard;
    private SimpleDateFormat dateFormat;
    private String filePath = "visitor_records.txt";
   

    public GuardFunctionGUI() {
        guard = new GuardFunction();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initializeUI();
        
    }
    
    
    
    
    private void initializeUI() {
        setTitle("Guard System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(15);
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel phoneLabel = new JLabel("Phone Number:");
        mainPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;

        phoneField = new JTextField(15);
        mainPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel plateNumberLabel = new JLabel("Car Plate Number:");
        mainPanel.add(plateNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;

        plateNumberField = new JTextField(15);
        mainPanel.add(plateNumberField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        
     
        gbc.gridx = 0;
        gbc.gridy = 3;

        JLabel destinationLabel = new JLabel("Destination:");
        mainPanel.add(destinationLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;

        destinationField = new JTextField(15);
        mainPanel.add(destinationField, gbc);


        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneField.getText();
                String plateNumber = plateNumberField.getText();
                String destination = destinationField.getText();
                
                guard.registerVisitor(name, phoneNumber, plateNumber, destination);
                Object[] rowData = {name, phoneNumber, plateNumber, destination, dateFormat.format(new Date()), ""};
                tableModel.addRow(rowData);
                
                saveVisitorDetailsToFile(name, phoneNumber, plateNumber, destination); 
                clearFields();
                
                JOptionPane.showMessageDialog(GuardFunctionGUI.this, "Visitor information successfully registered."
                        ,"Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mainPanel.add(registerButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;

        JButton signOutButton = new JButton("Time Out");
        signOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = visitorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 0);
                    guard.signOutVisitor(name);
                    updateVisitorTimeOut(name);
                    tableModel.setValueAt(dateFormat.format(new Date()), selectedRow, 5);
                    clearFields();
                }
            }
        });
        mainPanel.add(signOutButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = visitorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 0);
                    guard.removeVisitor(name);
                    tableModel.removeRow(selectedRow);
                    clearFields();
                }
            }
        });
        mainPanel.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Car Plate Number");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Time In");
        tableModel.addColumn("Time Out");

        visitorTable = new JTable(tableModel);
        visitorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(visitorTable);
        mainPanel.add(scrollPane, gbc);

        add(mainPanel);
    }
    
      
    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        plateNumberField.setText("");
        destinationField.setText("");
    }
    
   
    private void saveVisitorDetailsToFile(String name, String phoneNumber, String carPlateNumber, String destination) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Name: " + name);
            writer.println("Phone Number: " + phoneNumber);
            writer.println("Car Plate Number: " + carPlateNumber);
            writer.println("Visitor Destination: " + destination);
            writer.println("Time In: " + dateFormat.format(new Date()));
            writer.println();
            System.out.println("Visitor details saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error occurred while saving visitor details to file: " + e.getMessage());
        }
    }
    
        private void updateVisitorTimeOut(String name) {
    try {
        File file = new File(filePath);
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith("Name: " + name)) {
                writer.println(currentLine);
                writer.println("Time Out: " + dateFormat.format(new Date()));
                writer.println(); 
            } else {
                writer.println(currentLine);
            }
        }

        writer.close();
        reader.close();

        // Delete the original file
        if (file.delete()) {
            // Rename the temporary file to the original file name
            if (tempFile.renameTo(file)) {
                System.out.println("Visitor time out updated in file: " + filePath);
            } else {
                System.out.println("Error occurred while renaming the file.");
            }
        } else {
            System.out.println("Error occurred while deleting the file.");
        }
    } catch (IOException e) {
        System.out.println("Error occurred while updating visitor time out in file: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        try {
           
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                    GuardFunctionGUI guardFunctionGUI = new GuardFunctionGUI();
                    
                    guardFunctionGUI.setVisible(true);
                
            }
        });
    }

   
}
