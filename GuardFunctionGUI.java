import java.awt.*;
import java.awt.event.*;
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
    private Date timeIn;
    private Date timeOut;

    public Visitor(String name, String phoneNumber, String carPlateNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carPlateNumber = carPlateNumber;
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

    public void registerVisitor(String name, String phoneNumber, String carPlateNumber) {
        Visitor visitor = new Visitor(name, phoneNumber, carPlateNumber);
        visitors.add(visitor);
        System.out.println("Visitor " + name + " registered at " + visitor.getTimeIn() + " with car plate number: " + carPlateNumber);
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
        setSize(800, 400);
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
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneField.getText();
                String plateNumber = plateNumberField.getText();
                guard.registerVisitor(name, phoneNumber, plateNumber);
                Object[] rowData = {name, phoneNumber, plateNumber, dateFormat.format(new Date()), ""};
                tableModel.addRow(rowData);
                saveVisitorDetailsToFile(name, phoneNumber, plateNumber);
                clearFields();
                
                JOptionPane.showMessageDialog(GuardFunctionGUI.this, "Visitor information successfully registered."
                        ,"Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mainPanel.add(registerButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;

        JButton signOutButton = new JButton("Time Out");
        signOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = visitorTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 0);
                    guard.signOutVisitor(name);
                    tableModel.setValueAt(dateFormat.format(new Date()), selectedRow, 4);
                    clearFields();
                }
            }
        });
        mainPanel.add(signOutButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;

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
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Car Plate Number");
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
    }

    private void saveVisitorDetailsToFile(String name, String phoneNumber, String carPlateNumber) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Name: " + name);
            writer.println("Phone Number: " + phoneNumber);
            writer.println("Car Plate Number: " + carPlateNumber);
            writer.println("Time In: " + dateFormat.format(new Date()));
            writer.println();
            System.out.println("Visitor details saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error occurred while saving visitor details to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            // Set the look and feel to the system's default
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
