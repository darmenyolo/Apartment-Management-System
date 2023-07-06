import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class CreateResidentAccountGUI extends JFrame {
    private JTextField nameField;
    private JTextField contactNumberField;
    private JTextField idNumberField;
    private JTextField carPlateNumberField;
    private JTextField emailField;
    private JPasswordField passField;
    private JTextField hseAddress;

    public CreateResidentAccountGUI() {
        setTitle("Create Resident Account");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel nameLabel = new JLabel("Resident Name:");
        nameField = new JTextField(20);

        JLabel contactNumberLabel = new JLabel("Contact Number:");
        contactNumberField = new JTextField(20);

        JLabel idNumberLabel = new JLabel("ID Number:");
        idNumberField = new JTextField(20);

        JLabel carPlateNumberLabel = new JLabel("Car Plate Number:");
        carPlateNumberField = new JTextField(20);
        
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        
        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField(20);
        
        JLabel addressLabel = new JLabel("House Address:");
        hseAddress = new JTextField(20);

        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                    
                insertResidentDetails();
                // Perform the necessary actions to create a resident account
               
                // Reset the fields
                nameField.setText("");
                contactNumberField.setText("");
                idNumberField.setText("");
                carPlateNumberField.setText("");
                emailField.setText("");
                passField.setText("");
                hseAddress.setText("");
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(contactNumberLabel);
        mainPanel.add(contactNumberField);
        mainPanel.add(idNumberLabel);
        mainPanel.add(idNumberField);
        mainPanel.add(carPlateNumberLabel);
        mainPanel.add(carPlateNumberField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(passLabel);
        mainPanel.add(passField);
        mainPanel.add(addressLabel);
        mainPanel.add(hseAddress);
        
        mainPanel.add(createButton);
        mainPanel.add(cancelButton);
        //NEED TO ADD EMAIL

        add(mainPanel);
    }
    
    public void insertResidentDetails() {
    String name = nameField.getText();
    String contactNumber = contactNumberField.getText();
    String idNumber = idNumberField.getText();
    String carPlateNumber = carPlateNumberField.getText();
    String email = emailField.getText();
    String pass = new String(passField.getPassword());
    String hseaddress = hseAddress.getText();

    try {
        Connection con = DBConnection.getConnection();

        // Check if the username already exists
        String usernameCheckQuery = "SELECT COUNT(*) FROM residents WHERE uName = ?";
        PreparedStatement usernameCheckStmt = con.prepareStatement(usernameCheckQuery);
        usernameCheckStmt.setString(1, name);
        ResultSet usernameCheckResult = usernameCheckStmt.executeQuery();
        usernameCheckResult.next();
        int usernameCount = usernameCheckResult.getInt(1);

        if (usernameCount > 0) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Username Exists", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert the resident details if the username doesn't exist
        String insertQuery = "INSERT INTO residents(uName, idNum, contactNum, carPlate, email, password, hseaddress) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertStmt = con.prepareStatement(insertQuery);
        insertStmt.setString(1, name);
        insertStmt.setString(2, contactNumber);
        insertStmt.setString(3, idNumber);
        insertStmt.setString(4, carPlateNumber);
        insertStmt.setString(5, email);
        insertStmt.setString(6, pass);
        insertStmt.setString(7, hseaddress);

        int updatedRowCount = insertStmt.executeUpdate();

        if (updatedRowCount > 0) {
            JOptionPane.showMessageDialog(this, "Account created successfully", "Account Created", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create account", "Account Creation Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (HeadlessException | SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CreateResidentAccountGUI createResidentAccountGUI = new CreateResidentAccountGUI();
                createResidentAccountGUI.setVisible(true);
            }
        });
    }
}
