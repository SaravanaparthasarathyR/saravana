import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ViewStudent {

    JFrame f;
    JTable table;

    ViewStudent() {

        f = new JFrame("View Students");
        f.setSize(800, 400);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // ===== DARK BACKGROUND =====
        f.getContentPane().setBackground(new Color(18, 32, 64));

        table = new JTable();
        table.setBackground(new Color(230, 235, 245));
        table.setForeground(Color.BLACK);
        table.setRowHeight(25);

        // ===== TABLE HEADER STYLE =====
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(25, 45, 85));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 740, 250);
        sp.getViewport().setBackground(new Color(230, 235, 245));
        f.add(sp);

        loadStudentData();

        // ===== BACK BUTTON =====
        JButton back = new JButton("Back");
        back.setBounds(330, 300, 120, 40);
        styleButton(back);
        f.add(back);

        back.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadStudentData() {

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            DefaultTableModel model = new DefaultTableModel();

            // Column names
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rsmd.getColumnName(i));
            }

            // Rows
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

            table.setModel(model);

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading student data");
        }
    }

    // ===== BUTTON STYLE =====
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(52, 120, 246));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}