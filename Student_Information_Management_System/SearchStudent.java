import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchStudent {

    SearchStudent() {

        JFrame f = new JFrame("Search Student");
        f.setSize(500,550);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // ===== DARK BACKGROUND =====
        f.getContentPane().setBackground(new Color(18, 32, 64));

        // ===== INPUT FIELD =====
        JLabel idLabel = new JLabel("Student ID");
        idLabel.setBounds(50,40,100,30);
        idLabel.setForeground(Color.WHITE);
        f.add(idLabel);

        JTextField student_id = new JTextField();
        student_id.setBounds(150,40,200,30);
        student_id.setBackground(new Color(230,235,245));
        f.add(student_id);

        // ===== RESULT LABELS =====
        JLabel name = createLabel("Name:", 100);
        JLabel department = createLabel("Department:", 140);
        JLabel a_year = createLabel("Year:", 180);
        JLabel dob = createLabel("DOB:", 220);
        JLabel age = createLabel("Age:", 260);
        JLabel gender = createLabel("Gender:", 300);
        JLabel email = createLabel("Email:", 340);

        f.add(name);
        f.add(department);
        f.add(a_year);
        f.add(dob);
        f.add(age);
        f.add(gender);
        f.add(email);

        // ===== BUTTONS =====
        JButton search = new JButton("Search");
        search.setBounds(80,400,120,40);
        styleButton(search);
        f.add(search);

        JButton back = new JButton("Back");
        back.setBounds(250,400,120,40);
        styleButton(back);
        f.add(back);

        // ===== SEARCH ACTION =====
        search.addActionListener(e -> {

            try {
                Connection con = DBConnection.getConnection();

                String sql = "SELECT * FROM students WHERE student_id=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,student_id.getText());

                ResultSet rs = pst.executeQuery();

                if(rs.next()) {

                    name.setText("Name: " + rs.getString("name"));
                    department.setText("Department: " + rs.getString("department"));
                    a_year.setText("Year: " + rs.getString("a_year"));
                    dob.setText("DOB: " + rs.getString("dob"));
                    age.setText("Age: " + rs.getString("age"));
                    gender.setText("Gender: " + rs.getString("gender"));
                    email.setText("Email: " + rs.getString("email"));

                } else {
                    JOptionPane.showMessageDialog(f,"Student Not Found");
                }

                con.close();

            } catch(Exception ex){
                JOptionPane.showMessageDialog(f,"Error: "+ex.getMessage());
            }
        });

        // ===== BACK BUTTON =====
        back.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ===== LABEL STYLE =====
    private JLabel createLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 400, 30);
        label.setForeground(Color.WHITE);
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