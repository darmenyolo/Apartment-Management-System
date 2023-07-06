import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class ResidentManagementForumGUI extends JFrame {
    
    private JTextArea discussionTextArea;
    private JTextArea displayTextArea;
    private JScrollPane displayScrollPane; // Declare the displayScrollPane

    public ResidentManagementForumGUI() {
        initializeUI();
        displayDiscussions();
    }

    private void initializeUI() {
        setTitle("Resident Management Forum");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel nameLabel = new JLabel("Name:");
        JLabel contactNumLabel = new JLabel("Contact Number:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel discussionLabel = new JLabel("Discussion:");

        JTextField nameTextField = new JTextField(20);
        JTextField contactNumTextField = new JTextField(20);
        JTextField emailTextField = new JTextField(20);

        discussionTextArea = new JTextArea(5, 50);
        JScrollPane discussionScrollPane = new JScrollPane(discussionTextArea);
        
        JButton postButton = new JButton("Post Discussion");

        displayTextArea = new JTextArea(10, 20);
        displayTextArea.setEditable(false);
        displayScrollPane = new JScrollPane(displayTextArea); // Initialize the displayScrollPane

        // GridBagLayout Constraints for Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(nameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(contactNumLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(emailLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(discussionLabel, gbc);

        // GridBagLayout Constraints for Text Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(nameTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(contactNumTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPane.add(emailTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPane.add(discussionScrollPane, gbc);

        // GridBagLayout Constraints for Post Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 0, 0);
        contentPane.add(postButton, gbc);

        postButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String contactNum = contactNumTextField.getText();
                String email = emailTextField.getText();
                String discussion = discussionTextArea.getText();
                postDiscussion(name, contactNum, email, discussion);
            }
        });

        // GridBagLayout Constraints for Display Text Area
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        contentPane.add(displayScrollPane, gbc);

        setContentPane(contentPane);
    }

    private void displayDiscussions() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT name, contactNum, email, message FROM managementforum";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder displayText = new StringBuilder();
            while (rs.next()) {
                String name = rs.getString("name");
                String contactNum = rs.getString("contactNum");
                String email = rs.getString("email");
                String message = rs.getString("message");
                
                displayText.append("Name: ").append(name).append("\n");
                displayText.append("Contact Number: ").append(contactNum).append("\n");
                displayText.append("Email: ").append(email).append("\n");
                displayText.append("Discussion:\n\n").append(message).append("\n");
                displayText.append("-------------------------------").append("\n\n");
            }

            
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 13); 
            displayTextArea.setFont(font);
            displayTextArea.setText(displayText.toString());
            displayTextArea.setLineWrap(true); 
            displayTextArea.setWrapStyleWord(true); 
            displayTextArea.setCaretPosition(0); 

            // Disable horizontal scrolling
            displayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postDiscussion(String name, String contactNum, String email, String message) {
        
       
        if (name.isEmpty() || contactNum.isEmpty() || email.isEmpty() || message.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill up all fields", "Incomplete Information", JOptionPane.WARNING_MESSAGE);
        return; 
        }
        
        
        
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO managementforum (name, contactNum, email, message) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, contactNum);
            stmt.setString(3, email);
            stmt.setString(4, message);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Discussion posted successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                displayDiscussions(); // Refresh the displayed discussions
                discussionTextArea.setText(""); // Clear the discussion text area
            } else {
                JOptionPane.showMessageDialog(this, "Failed to post discussion",
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
                ResidentManagementForumGUI forumGUI = new ResidentManagementForumGUI();
                forumGUI.setVisible(true);
            }
        });
    }
}
