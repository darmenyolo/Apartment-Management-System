import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class ResidentAccountInfoGUI extends JFrame {
    
    private ResidentHomescreenGUI residentscreenGUI;
    private String residentUsername;

    private JTextField usernameTextField;
    private JTextField idNumTextField;
    private JTextField contactNumTextField;
    private JTextField carPlateTextField;
    private JTextField emailTextField;
    private JTextField hseAddressTextField;
    private JPasswordField passwordField;

    public ResidentAccountInfoGUI(String username) {
        this.residentUsername = username;
        initializeUI();
        displayResidentInfo();
    }

    private void initializeUI() {
        setTitle("Resident Account Information");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel idNumLabel = new JLabel("ID Number:");
        JLabel contactNumLabel = new JLabel("Contact Number:");
        JLabel carPlateLabel = new JLabel("Car Plate:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel hseAddressLabel = new JLabel("House Address:");
        JLabel passwordLabel = new JLabel("Password:");

        

        // Text Fields
        usernameTextField = new JTextField(20);
        idNumTextField = new JTextField(20);
        contactNumTextField = new JTextField(20);
        carPlateTextField = new JTextField(20);
        emailTextField = new JTextField(20);
        hseAddressTextField = new JTextField(20);
        passwordField = new JPasswordField(20);


        // Set larger font size
        Font labelFont = usernameLabel.getFont().deriveFont(Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        usernameLabel.setFont(labelFont);
        idNumLabel.setFont(labelFont);
        contactNumLabel.setFont(labelFont);
        carPlateLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        hseAddressLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        
        
        usernameTextField.setFont(textFieldFont);
        idNumTextField.setFont(textFieldFont);
        contactNumTextField.setFont(textFieldFont);
        carPlateTextField.setFont(textFieldFont);
        emailTextField.setFont(textFieldFont);
        hseAddressTextField.setFont(textFieldFont);
        passwordField.setFont(textFieldFont);

        // GridBagLayout Constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(usernameLabel, gbc);

        gbc.gridx = 1;
        contentPane.add(usernameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(idNumLabel, gbc);

        gbc.gridx = 1;
        contentPane.add(idNumTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(contactNumLabel, gbc);

        gbc.gridx = 1;
        contentPane.add(contactNumTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(carPlateLabel, gbc);

        gbc.gridx = 1;
        contentPane.add(carPlateTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(emailLabel, gbc);

        gbc.gridx = 1;
        contentPane.add(emailTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPane.add(hseAddressLabel, gbc);

        gbc.gridx = 1;
        contentPane.add(hseAddressTextField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPane.add(passwordLabel, gbc);

        gbc.gridx = 1;
        contentPane.add(passwordField, gbc);

        // Update Button
        JButton updateButton = new JButton("Update");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 0, 0);
        contentPane.add(updateButton, gbc);

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateResidentDetails();
            }
        });
        
        setContentPane(contentPane);
    }

    private void displayResidentInfo() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM residents WHERE uName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, residentUsername);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("uName");
                String idNum = rs.getString("idNum");
                String contactNum = rs.getString("contactNum");
                String carPlate = rs.getString("carPlate");
                String email = rs.getString("email");
                String hseAddress = rs.getString("hseAddress");
                String pass = rs.getString("password");

                usernameTextField.setText(username);
                idNumTextField.setText(idNum);
                contactNumTextField.setText(contactNum);
                carPlateTextField.setText(carPlate);
                emailTextField.setText(email);
                hseAddressTextField.setText(hseAddress);
                passwordField.setText(pass);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateResidentDetails() {
        try (Connection conn = DBConnection.getConnection()) {
            String updateQuery = "UPDATE residents SET idNum = ?, contactNum = ?, carPlate = ?, email = ?, password = ?, hseAddress = ? WHERE uName = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);

            updateStmt.setString(1, idNumTextField.getText());
            updateStmt.setString(2, contactNumTextField.getText());
            updateStmt.setString(3, carPlateTextField.getText());
            updateStmt.setString(4, emailTextField.getText());
            updateStmt.setString(5, new String(passwordField.getPassword()));
            updateStmt.setString(6, hseAddressTextField.getText());
            updateStmt.setString(7, residentUsername);

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Details updated successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update details",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
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
                ResidentAccountInfoGUI accountInfoGUI = new ResidentAccountInfoGUI("");
                accountInfoGUI.setVisible(true);
            }
        });
    }
}
