import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddMark {

    Connection conn;

    AddMark() {

        JFrame f = new JFrame("Add Marks (All Subjects)");
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
        JLabel title = new JLabel("ADD MARKS", SwingConstants.CENTER);
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

        // ===== ADD BUTTON =====
        JButton addBtn = new JButton("Add Marks");
        addBtn.setBounds(100, 300, 120, 35);
        addBtn.setBackground(new Color(52, 120, 246));
        addBtn.setForeground(Color.WHITE);
        panel.add(addBtn);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(240, 300, 100, 35);
        backBtn.setBackground(new Color(200, 50, 70));
        backBtn.setForeground(Color.WHITE);
        panel.add(backBtn);

        // ===== ADD ACTION =====
        addBtn.addActionListener(e -> {
            try {
                String email = userField.getText().trim();

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "Enter Gmail!");
                    return;
                }

                if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                    JOptionPane.showMessageDialog(f, "Enter valid Gmail!");
                    return;
                }

                int java = Integer.parseInt(fields[0].getText());
                int python = Integer.parseInt(fields[1].getText());
                int cpp = Integer.parseInt(fields[2].getText());
                int ai = Integer.parseInt(fields[3].getText());
                int dbms = Integer.parseInt(fields[4].getText());

                String sql = "INSERT INTO Marks VALUES (?,?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setInt(2, java);
                pst.setInt(3, python);
                pst.setInt(4, cpp);
                pst.setInt(5, ai);
                pst.setInt(6, dbms);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(f, "Marks Added Successfully");

                // 🔥 CLEAR ALL FIELDS AFTER INSERT
                userField.setText("");
                for (JTextField field : fields) {
                    field.setText("");
                }

                userField.requestFocus(); // optional focus

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