import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomescreenGUI extends JFrame {
    private GuardFunctionGUI guardFunctionGUI;

    public HomescreenGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Apartment Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Guard Function");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (guardFunctionGUI == null) {
                    guardFunctionGUI = new GuardFunctionGUI();
                }
                guardFunctionGUI.setVisible(true);
            }
        });
        fileMenu.add(openItem);
        menuBar.add(fileMenu);
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
                HomescreenGUI homescreenGUI = new HomescreenGUI();
                homescreenGUI.setVisible(true);
            }
        });
    }
}
