import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResidentHomescreenGUI extends JFrame {
   
    private ResidentAccountInfoGUI residentinfoGUI;
    private ResidentManagementForumGUI managementforumGUI;
    private ResidentDiscussionForumGUI residentforumGUI;
    private BillFunctionGUI billfunctionGUI;
    private String residentUsername; 
    

    public ResidentHomescreenGUI(String username) {
        this.residentUsername = username;
        initializeUI();
        displayWelcomeMessage();
    }

    private void initializeUI() {
        setTitle("Sparrow Apartment Resident System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.LIGHT_GRAY);
        
        
         // Background image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/pic1.jpg")); // Replace "background.jpg" with the path to your image
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(200, 150, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        getLayeredPane().add(backgroundLabel, Integer.valueOf(Integer.MIN_VALUE));

        // Content pane customization
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setOpaque(false);
        
        
        
        

       

        JMenuBar menuBar = new JMenuBar();
        
        JMenu guardfunctionMenu = new JMenu("Resident Function");
        JMenuItem openManagementForum = new JMenuItem("Open Management Forum");
        openManagementForum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             //ADD ACTION FOR MANAGEMENT FORUM
                if (managementforumGUI == null) {
             managementforumGUI = new ResidentManagementForumGUI();
                }
             managementforumGUI.setVisible(true);
            }
        });
        
        JMenuItem openResidentForum = new JMenuItem("Open Resident Forum");
        openResidentForum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //ADD ACTION FOR RESIDENT FORUM
                
                if (residentforumGUI == null) {
             residentforumGUI = new ResidentDiscussionForumGUI();
                }
             residentforumGUI.setVisible(true);
            }
        });
        
        JMenuItem openAccountInfo = new JMenuItem("Open Resident Account Info");
        openAccountInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //ADD ACTION FOR RESIDENT ACCOUNT INFO
             if (residentinfoGUI == null) {
             residentinfoGUI = new ResidentAccountInfoGUI(residentUsername);
                }
             residentinfoGUI.setVisible(true);
            }
        });
        
        JMenuItem openBillPayment = new JMenuItem("Open Bill Payment Function");
        openBillPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                //ADD ACTION FOR BILL PAYMENT FUNCTION
                
                if (billfunctionGUI == null) {
             billfunctionGUI = new BillFunctionGUI();
                }
             billfunctionGUI.setVisible(true);
            }
        });
        
        
        
        guardfunctionMenu.add(openManagementForum);
        guardfunctionMenu.add(openResidentForum);
        guardfunctionMenu.add(openAccountInfo);
        guardfunctionMenu.add(openBillPayment);
        
        menuBar.add(guardfunctionMenu);
        setJMenuBar(menuBar);
    }
    
    
    private void displayWelcomeMessage() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM residents WHERE uName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, residentUsername);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String residentName = rs.getString("uName");
                String welcomeMessage = "Welcome, " + residentName + " to Sparrow Apartment Resident System!";
                JOptionPane.showMessageDialog(this, welcomeMessage, "Sparrow Apartment Resident System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
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
                ResidentHomescreenGUI residentscreenGUI = new ResidentHomescreenGUI("");
                residentscreenGUI.setVisible(true);
            }
        });
    }
}
