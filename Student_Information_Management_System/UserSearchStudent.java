import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserSearchStudent {

    String emailUser;

    UserSearchStudent(String emailUser) {

        this.emailUser = emailUser;

        JFrame f = new JFrame("My Details");
        f.setSize(500,520);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // ===== DARK BACKGROUND =====
        f.getContentPane().setBackground(new Color(18, 32, 64));

        // ===== TITLE =====
        JLabel title = new JLabel("MY DETAILS");
        title.setBounds(180,20,200,30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        f.add(title);

        Color textColor = Color.WHITE;

        JLabel student_idLabel = createLabel("Student ID:", 70, textColor);
        JLabel nameLabel = createLabel("Name:", 110, textColor);
        JLabel departmentLabel = createLabel("Department:", 150, textColor);
        JLabel a_yearLabel = createLabel("Academic Year:", 190, textColor);
        JLabel dobLabel = createLabel("DOB:", 230, textColor);
        JLabel ageLabel = createLabel("Age:", 270, textColor);
        JLabel genderLabel = createLabel("Gender:", 310, textColor);
        JLabel emailLabel = createLabel("Email:", 350, textColor);

        f.add(student_idLabel);
        f.add(nameLabel);
        f.add(departmentLabel);
        f.add(a_yearLabel);
        f.add(dobLabel);
        f.add(ageLabel);
        f.add(genderLabel);
        f.add(emailLabel);

        // ===== BUTTON =====
        JButton back = new JButton("Back");
        back.setBounds(200,420,100,40);
        styleButton(back);
        f.add(back);

        // ===== DATABASE =====
        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            String url = "jdbc:ucanaccess://E:/tech/studentdb.accdb";
            Connection con = DriverManager.getConnection(url);

            String sql = "SELECT * FROM [students] WHERE LCASE([email]) = LCASE(?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, emailUser.trim());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                student_idLabel.setText("Student ID: " + rs.getInt("student_id"));
                nameLabel.setText("Name: " + rs.getString("name"));
                departmentLabel.setText("Department: " + rs.getString("department"));
                a_yearLabel.setText("Academic Year: " + rs.getString("a_year"));
                dobLabel.setText("DOB: " + rs.getString("dob"));
                ageLabel.setText("Age: " + rs.getString("age"));
                genderLabel.setText("Gender: " + rs.getString("gender"));
                emailLabel.setText("Email: " + rs.getString("email"));

            } else {
                JOptionPane.showMessageDialog(f, "No record found for this user.");
            }

            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Database Error: " + ex.getMessage());
        }

        // ===== BACK ACTION =====
        back.addActionListener(e -> {
            f.dispose();
            new UserDashboard(emailUser.trim());
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ===== LABEL STYLE =====
    private JLabel createLabel(String text, int y, Color color) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 400, 30);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    // ===== BUTTON STYLE =====
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(52, 120, 246));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}