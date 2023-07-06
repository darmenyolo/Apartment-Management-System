import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuardHomescreenGUI extends JFrame {
    private GuardFunctionGUI guardFunctionGUI;
    

    public GuardHomescreenGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Guard Management System");
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
        
        JMenu guardfunctionMenu = new JMenu("Guard Function");
        JMenuItem openItem = new JMenuItem("Open Guard Function");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (guardFunctionGUI == null) {
                    guardFunctionGUI = new GuardFunctionGUI();
                }
                guardFunctionGUI.setVisible(true);
            }
        });
        
        
        
        guardfunctionMenu.add(openItem);
      
        
        menuBar.add(guardfunctionMenu);
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
                GuardHomescreenGUI homescreenGUI = new GuardHomescreenGUI();
                homescreenGUI.setVisible(true);
            }
        });
    }
}
