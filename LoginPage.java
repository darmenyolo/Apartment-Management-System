import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private GuardFunctionGUI guardFunctionGUI;
    private GuardHomescreenGUI guardhomeScreenGUI;
    private LoginPage loginpageGUI;
    private ManagerHomescreenGUI managerscreenGUI;
    private ResidentHomescreenGUI residentscreenGUI;
    private JComboBox<String> userTypeDropdown; // Dropdown for user type selection
    
    public LoginPage() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        
        Font labelFont = new Font(Font.DIALOG, Font.BOLD, 16);
        Font textFieldFont = new Font(Font.DIALOG, Font.PLAIN, 16);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(textFieldFont);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(textFieldFont);
        panel.add(passwordField);

        // User type dropdown
        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setFont(labelFont);
        panel.add(userTypeLabel);

        userTypeDropdown = new JComboBox<>(new String[]{"Guard", "Apartment Manager", "Resident"});
        userTypeDropdown.setFont(textFieldFont);
        panel.add(userTypeDropdown);

        JButton loginBtn = new JButton("Login"); // Login button
        loginBtn.addActionListener(this);
        loginBtn.setFont(labelFont);
        panel.add(loginBtn);

        add(panel);
        pack();
        setSize(500, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText(); //ENTER USERNAME
        String password = new String(passwordField.getPassword()); //ENTER PASSWORD
        String selectedUserType = (String) userTypeDropdown.getSelectedItem(); // SELECT A USER TYPE

        if (selectedUserType.equals("Guard")) {
            // TODO: Perform guard login logic
            try (Connection conn = DBConnection.getConnection()) {
                String query = "SELECT * FROM guard WHERE uname = ? AND guardpass = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Guard login successful");
                    guardhomeScreenGUI = new GuardHomescreenGUI();
                    guardhomeScreenGUI.setVisible(true);
                    dispose();
                } else {
                    System.out.println("Invalid username or password");
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (selectedUserType.equals("Apartment Manager")) {
            // TODO: Perform apartment manager login logic
            try (Connection conn = DBConnection.getConnection()) {
                String query = "SELECT * FROM manager WHERE uname = ? AND managerpass = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Manager Login successful");
                    managerscreenGUI = new ManagerHomescreenGUI();
                    managerscreenGUI.setVisible(true);
                    dispose();
                } else {
                    System.out.println("Invalid username or password");
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (selectedUserType.equals("Resident")) {
            // TODO: Perform resident login logic
            try (Connection conn = DBConnection.getConnection()) {
                String query = "SELECT * FROM residents WHERE uName = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Resident Login successful");
                    residentscreenGUI  = new ResidentHomescreenGUI();
                    residentscreenGUI.setVisible(true);
                    dispose();
                } else {
                    System.out.println("Invalid username or password");
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    
    }
}
