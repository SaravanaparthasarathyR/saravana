import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchMark {

    Connection conn;

    SearchMark() {

        JFrame f = new JFrame("Search Student Marks");
        f.setSize(450, 420);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // ===== DARK BLUE BACKGROUND =====
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 450, 420);
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
        JLabel title = new JLabel("SEARCH MARKS", SwingConstants.CENTER);
        title.setBounds(100, 10, 250, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(title);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // ===== EMAIL INPUT =====
        JLabel userLabel = new JLabel("Student Gmail");
        userLabel.setBounds(50, 60, 120, 20);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(labelFont);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(200, 60, 150, 25);
        panel.add(userField);

        // ===== SEARCH BUTTON =====
        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(150, 100, 120, 35);
        searchBtn.setBackground(new Color(52, 120, 246));
        searchBtn.setForeground(Color.WHITE);
        panel.add(searchBtn);

        // ===== RESULT LABELS =====
        String subjects[] = {"Java", "Python", "C++", "AI", "DBMS"};
        JLabel values[] = new JLabel[5];

        int y = 160;

        for (int i = 0; i < subjects.length; i++) {

            JLabel sub = new JLabel(subjects[i] + " :");
            sub.setBounds(100, y, 100, 25);
            sub.setForeground(Color.WHITE);
            sub.setFont(labelFont);
            panel.add(sub);

            values[i] = new JLabel("-");
            values[i].setBounds(220, y, 100, 25);
            values[i].setForeground(Color.YELLOW);
            values[i].setFont(labelFont);
            panel.add(values[i]);

            y += 35;
        }

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(160, 330, 120, 35);
        backBtn.setBackground(new Color(200, 50, 70));
        backBtn.setForeground(Color.WHITE);
        panel.add(backBtn);

        // ===== SEARCH ACTION =====
        searchBtn.addActionListener(e -> {
            try {
                String email = userField.getText().trim();

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "Enter Gmail!");
                    return;
                }

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
                    JOptionPane.showMessageDialog(f, "Student not found!");

                    // clear result labels
                    for (JLabel val : values) {
                        val.setText("-");
                    }
                }

                // 🔥 CLEAR USERNAME FIELD AFTER SEARCH
                userField.setText("");
                userField.requestFocus();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Error: " + ex.getMessage());
            }
        });

        // ===== BACK ACTION =====
        backBtn.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        f.setVisible(true);
    }
}