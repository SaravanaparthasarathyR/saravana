import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateMark {

    Connection conn;

    UpdateMark() {

        JFrame f = new JFrame("Update Marks");
        f.setSize(450, 450);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // ===== DARK BLUE BACKGROUND =====
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 450, 450);
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
        JLabel title = new JLabel("UPDATE MARKS", SwingConstants.CENTER);
        title.setBounds(100, 10, 250, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(title);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        // ===== EMAIL =====
        JLabel userLabel = new JLabel("Student Gmail");
        userLabel.setBounds(50, 60, 120, 20);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(labelFont);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(200, 60, 150, 25);
        panel.add(userField);

        // ===== FETCH BUTTON =====
        JButton fetchBtn = new JButton("Fetch");
        fetchBtn.setBounds(360, 60, 70, 25);
        fetchBtn.setBackground(new Color(100, 180, 255));
        panel.add(fetchBtn);

        // ===== SUBJECT FIELDS =====
        String subjects[] = {"Java", "Python", "C++", "AI", "DBMS"};
        JTextField fields[] = new JTextField[5];

        int y = 100;

        for (int i = 0; i < subjects.length; i++) {
            JLabel lbl = new JLabel(subjects[i]);
            lbl.setBounds(50, y, 120, 20);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(labelFont);
            panel.add(lbl);

            fields[i] = new JTextField();
            fields[i].setBounds(200, y, 150, 25);
            panel.add(fields[i]);

            y += 40;
        }

        // ===== UPDATE BUTTON =====
        JButton updateBtn = new JButton("Update Marks");
        updateBtn.setBounds(100, 320, 130, 35);
        updateBtn.setBackground(new Color(52, 120, 246));
        updateBtn.setForeground(Color.WHITE);
        panel.add(updateBtn);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(240, 320, 100, 35);
        backBtn.setBackground(new Color(200, 50, 70));
        backBtn.setForeground(Color.WHITE);
        panel.add(backBtn);

        // ===== FETCH ACTION =====
        fetchBtn.addActionListener(e -> {
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
                    fields[0].setText(String.valueOf(rs.getInt("Java")));
                    fields[1].setText(String.valueOf(rs.getInt("Python")));
                    fields[2].setText(String.valueOf(rs.getInt("Cpp")));
                    fields[3].setText(String.valueOf(rs.getInt("AI")));
                    fields[4].setText(String.valueOf(rs.getInt("DBMS")));
                } else {
                    JOptionPane.showMessageDialog(f, "Student not found!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Error: " + ex.getMessage());
            }
        });

        // ===== UPDATE ACTION =====
        updateBtn.addActionListener(e -> {
            try {
                String email = userField.getText().trim();

                int java = Integer.parseInt(fields[0].getText());
                int python = Integer.parseInt(fields[1].getText());
                int cpp = Integer.parseInt(fields[2].getText());
                int ai = Integer.parseInt(fields[3].getText());
                int dbms = Integer.parseInt(fields[4].getText());

                String sql = "UPDATE Marks SET Java=?, Python=?, Cpp=?, AI=?, DBMS=? WHERE Username=?";
                PreparedStatement pst = conn.prepareStatement(sql);

                pst.setInt(1, java);
                pst.setInt(2, python);
                pst.setInt(3, cpp);
                pst.setInt(4, ai);
                pst.setInt(5, dbms);
                pst.setString(6, email);

                int rows = pst.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(f, "Marks Updated Successfully");
                } else {
                    JOptionPane.showMessageDialog(f, "Student not found!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(f, "Enter valid numbers!");
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