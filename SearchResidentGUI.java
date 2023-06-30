import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class SearchResidentGUI extends JFrame implements ActionListener {

    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    public SearchResidentGUI() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        setTitle("Search Resident");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout(20, 20));

        JLabel titleLabel = new JLabel("Search Resident");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(20, 20));

        JLabel searchLabel = new JLabel("Enter Name/Car Plate/Contact/Email/House Address:");
        searchPanel.add(searchLabel, BorderLayout.WEST);

        searchField = new JTextField();
        searchField.setColumns(20);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                clearTable();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(clearButton);
        buttonPanel.add(searchButton);

        searchPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(searchPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        JScrollPane resultScrollPane = new JScrollPane(resultTable);

        panel.add(resultScrollPane, BorderLayout.SOUTH);

        add(panel);
        pack();
    }

    private void connectToDatabase() {
        connection = DBConnection.getConnection();
    }

    private void searchResidents(String searchQuery) {
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT idNum AS 'ID Number', " +
                        "uName AS 'Resident Name', " +
                        "carPlate AS 'Car Plate', " +
                        "contactNum AS 'Contact Num', " +
                        "email AS 'Email', " +
                        "hseaddress AS 'House Address' " +
                        "FROM residents WHERE " +
                        "idNum LIKE '%" + searchQuery + "%' OR " +
                        "uName LIKE '%" + searchQuery + "%' OR " +
                        "carPlate LIKE '%" + searchQuery + "%' OR " +
                        "contactNum LIKE '%" + searchQuery + "%' OR " +
                        "email LIKE '%" + searchQuery+ "%' OR " +
                        "hseaddress LIKE '%" + searchQuery + "%'";

                try (ResultSet resultSet = statement.executeQuery(query)) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // Set table column headers
                    String[] columnNames = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        columnNames[i - 1] = metaData.getColumnLabel(i);
                    }
                    tableModel.setColumnIdentifiers(columnNames);

                    // Clear existing rows
                    clearTable();

                    // Populate the table with data
                    while (resultSet.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = resultSet.getObject(i);
                        }
                        tableModel.addRow(rowData);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to execute the search query.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchResidentGUI::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String searchQuery = searchField.getText().trim();
        searchResidents(searchQuery);
    }
}
