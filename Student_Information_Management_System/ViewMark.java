import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewMark {

    Connection conn;

    ViewMark(String email) {

        JFrame f = new JFrame("View Marks");
        f.setSize(500, 400);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // ===== DARK BLUE BACKGROUND =====
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 500, 400);
        panel.setLayout(null);
        panel.setBackground(new Color(18, 32, 64));
        f.setContentPane(panel);

        // ===== DB CONNECTION =====
        try {
            String url = "jdbc:ucanaccess://E:/tech/studentdb.accdb";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "DB Error: " + e.getMessage());
        }

        // ===== TITLE =====
        JLabel title = new JLabel("MY MARKS", SwingConstants.CENTER);
        title.setBounds(150, 20, 200, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(title);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // ===== LABELS FOR SUBJECTS =====
        String subjects[] = {"Java", "Python", "C++", "AI", "DBMS"};
        JLabel values[] = new JLabel[5];

        int y = 80;

        for (int i = 0; i < subjects.length; i++) {

            JLabel subLabel = new JLabel(subjects[i] + " :");
            subLabel.setBounds(100, y, 100, 25);
            subLabel.setForeground(Color.WHITE);
            subLabel.setFont(labelFont);
            panel.add(subLabel);

            values[i] = new JLabel("-");
            values[i].setBounds(220, y, 100, 25);
            values[i].setForeground(Color.YELLOW);
            values[i].setFont(labelFont);
            panel.add(values[i]);

            y += 40;
        }

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(180, 300, 120, 35);
        backBtn.setBackground(new Color(200, 50, 70));
        backBtn.setForeground(Color.WHITE);
        panel.add(backBtn);

        // ===== FETCH DATA =====
        try {
            String sql = "SELECT * FROM Marks WHERE Username=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                values[0].setText(String.valueOf(rs.getInt("Java")));
                values[1].setText(String.valueOf(rs.getInt("Python")));
                values[2].setText(String.valueOf(rs.getInt("Cpp")));
                values[3].setText(String.valueOf(rs.getInt("AI")));
                values[4].setText(String.valueOf(rs.getInt("DBMS")));
            } else {
                JOptionPane.showMessageDialog(f, "No marks found!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error: " + e.getMessage());
        }

        // ===== BACK ACTION =====
        backBtn.addActionListener(e -> {
            f.dispose();
            new UserDashboard(email);
        });

        f.setVisible(true);
    }
}