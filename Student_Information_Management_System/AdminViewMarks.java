import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminViewMarks {

    Connection conn;

    AdminViewMarks() {

        JFrame f = new JFrame("All Students Marks");
        f.setSize(700, 450);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // ===== DARK BLUE BACKGROUND =====
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 700, 450);
        panel.setLayout(null);
        panel.setBackground(new Color(18, 32, 64));
        f.setContentPane(panel);

        // ===== TITLE =====
        JLabel title = new JLabel("ALL STUDENT MARKS", SwingConstants.CENTER);
        title.setBounds(150, 10, 400, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(title);

        // ===== TABLE =====
        String columns[] = {"Username", "Java", "Python", "C++", "AI", "DBMS"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 60, 630, 280);
        panel.add(scroll);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(280, 360, 120, 35);
        backBtn.setBackground(new Color(200, 50, 70));
        backBtn.setForeground(Color.WHITE);
        panel.add(backBtn);

        // ===== DB CONNECTION =====
        try {
            String url = "jdbc:ucanaccess://E:/tech/studentdb.accdb";
            conn = DriverManager.getConnection(url);

            String sql = "SELECT * FROM Marks";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String username = rs.getString("Username");
                int java = rs.getInt("Java");
                int python = rs.getInt("Python");
                int cpp = rs.getInt("Cpp");
                int ai = rs.getInt("AI");
                int dbms = rs.getInt("DBMS");

                model.addRow(new Object[]{username, java, python, cpp, ai, dbms});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error: " + e.getMessage());
        }

        // ===== BACK ACTION =====
        backBtn.addActionListener(e -> {
            f.dispose();
            new Dashboard(); // back to admin dashboard
        });

        f.setVisible(true);
    }
}