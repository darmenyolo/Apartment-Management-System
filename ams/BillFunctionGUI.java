import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BillFunctionGUI extends JFrame {

    private JComboBox<String> billTypeComboBox;
    private JRadioButton creditCardRadioButton;
    private JRadioButton payPalRadioButton;

    public BillFunctionGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bill Function");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel billTypeLabel = new JLabel("Bill Type:");
        JLabel paymentMethodLabel = new JLabel("Payment Method:");

        billTypeComboBox = new JComboBox<>(new String[]{"Monthly Maintenance Fees", "Sinking Fund"});
        billTypeComboBox.setSelectedIndex(-1);

        creditCardRadioButton = new JRadioButton("Credit Card");
        payPalRadioButton = new JRadioButton("PayPal");

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String billType = (String) billTypeComboBox.getSelectedItem();
                String paymentMethod = creditCardRadioButton.isSelected() ? "Credit Card" : "PayPal";

                if (billType == null || paymentMethod == null) {
                    JOptionPane.showMessageDialog(BillFunctionGUI.this,
                            "Please select both bill type and payment method", "Incomplete Information",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    // Process the payment and generate payment records
                    processPayment(billType, paymentMethod);
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(billTypeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(paymentMethodLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(billTypeComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(creditCardRadioButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(payPalRadioButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 0, 0);
        inputPanel.add(payButton, gbc);

        contentPane.add(inputPanel, BorderLayout.CENTER);

        setContentPane(contentPane);
    }

    private void processPayment(String billType, String paymentMethod) {
        // TODO: Implement payment processing logic here
        // Generate payment records and perform necessary actions
        System.out.println("Processing payment for bill type: " + billType);
        System.out.println("Payment method: " + paymentMethod);
        System.out.println("Generating payment records...");

        // Display success message
        JOptionPane.showMessageDialog(this, "Payment processed successfully",
                "Success", JOptionPane.INFORMATION_MESSAGE);

        // Reset the form
        billTypeComboBox.setSelectedIndex(-1);
        creditCardRadioButton.setSelected(false);
        payPalRadioButton.setSelected(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BillFunctionGUI billFunctionGUI = new BillFunctionGUI();
                billFunctionGUI.setVisible(true);
            }
        });
    }
}
