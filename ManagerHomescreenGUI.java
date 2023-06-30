import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ManagerHomescreenGUI extends JFrame {
    
    private CreateResidentAccountGUI residentaccGUI;
    private SearchResidentGUI searchResidentGUI;

    public ManagerHomescreenGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Apartment Management System");
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
        JMenu apartmentManagerMenu = new JMenu("Apartment Manager Function");
        JMenuItem createResidentMenuItem = new JMenuItem("Create Resident Account");
        createResidentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (residentaccGUI == null) {
                    residentaccGUI = new CreateResidentAccountGUI();
                }
                residentaccGUI.setVisible(true);
            
            }
});
        // NEED UPDATE SEARCHRESIDENTGUI
        
        JMenuItem searchResident = new JMenuItem("Search Resident Info");
        searchResident.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (searchResidentGUI == null) {
                    searchResidentGUI = new SearchResidentGUI();
                }
                searchResidentGUI.setVisible(true);
            
            }
});
        
        
        apartmentManagerMenu.add(createResidentMenuItem);
        apartmentManagerMenu.add(searchResident);
        
       
        menuBar.add(apartmentManagerMenu);
        setJMenuBar(menuBar);
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
                ManagerHomescreenGUI managerscreenGUI = new ManagerHomescreenGUI();
                managerscreenGUI.setVisible(true);
            }
        });
    }
}
