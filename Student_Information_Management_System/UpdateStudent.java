import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateStudent {

    UpdateStudent() {

        JFrame f = new JFrame("Update Student");
        f.setSize(520,650);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // ===== DARK BACKGROUND =====
        f.getContentPane().setBackground(new Color(18, 32, 64));

        Color labelColor = Color.WHITE;

        // ===== Student ID =====
        JLabel idLabel = new JLabel("Student ID");
        idLabel.setBounds(50,50,100,30);
        idLabel.setForeground(labelColor);
        f.add(idLabel);

        JTextField student_id = createField(150,50);
        f.add(student_id);

        JButton search = new JButton("Search");
        search.setBounds(360,50,100,30);
        styleButton(search);
        f.add(search);

        // ===== Other Fields =====
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(50,100,100,30);
        nameLabel.setForeground(labelColor);
        f.add(nameLabel);

        JTextField name = createField(150,100);
        f.add(name);

        JLabel deptLabel = new JLabel("Department");
        deptLabel.setBounds(50,150,100,30);
        deptLabel.setForeground(labelColor);
        f.add(deptLabel);

        JTextField department = createField(150,150);
        f.add(department);

        JLabel yearLabel = new JLabel("Year");
        yearLabel.setBounds(50,200,100,30);
        yearLabel.setForeground(labelColor);
        f.add(yearLabel);

        JComboBox<String> a_year = new JComboBox<>(new String[]{"Select","1st","2nd","3rd","4th"});
        a_year.setBounds(150,200,200,30);
        styleCombo(a_year);
        f.add(a_year);

        JLabel dobLabel = new JLabel("DOB (yyyy-mm-dd)");
        dobLabel.setBounds(50,250,150,30);
        dobLabel.setForeground(labelColor);
        f.add(dobLabel);

        JTextField dob = createField(200,250);
        f.add(dob);

        JLabel ageLabel = new JLabel("Age");
        ageLabel.setBounds(50,300,100,30);
        ageLabel.setForeground(labelColor);
        f.add(ageLabel);

        JTextField age = createField(150,300);
        f.add(age);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setBounds(50,350,100,30);
        genderLabel.setForeground(labelColor);
        f.add(genderLabel);

        JComboBox<String> gender = new JComboBox<>(new String[]{"Select","Male","Female","Other"});
        gender.setBounds(150,350,200,30);
        styleCombo(gender);
        f.add(gender);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50,400,100,30);
        emailLabel.setForeground(labelColor);
        f.add(emailLabel);

        JTextField email = createField(150,400);
        f.add(email);

        // ===== Buttons =====
        JButton update = new JButton("Update");
        update.setBounds(80,480,120,40);
        styleButton(update);
        f.add(update);

        JButton back = new JButton("Back");
        back.setBounds(250,480,120,40);
        styleButton(back);
        f.add(back);

        // ================= SEARCH ACTION =================
        search.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                String sql = "SELECT * FROM students WHERE student_id=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(student_id.getText()));

                ResultSet rs = pst.executeQuery();

                if(rs.next()) {
                    name.setText(rs.getString("name"));
                    department.setText(rs.getString("department"));
                    a_year.setSelectedItem(rs.getString("a_year"));
                    dob.setText(rs.getDate("dob").toString());
                    age.setText(String.valueOf(rs.getInt("age")));
                    gender.setSelectedItem(rs.getString("gender"));
                    email.setText(rs.getString("email"));
                } else {
                    JOptionPane.showMessageDialog(f,"Student ID Not Found");
                }

                con.close();

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(f,"Error: " + ex.getMessage());
            }
        });

        // ================= UPDATE ACTION =================
        update.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                String sql = "UPDATE students SET name=?, department=?, a_year=?, dob=?, age=?, gender=?, email=? WHERE student_id=?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, name.getText());
                pst.setString(2, department.getText());
                pst.setString(3, a_year.getSelectedItem().toString());
                pst.setDate(4, Date.valueOf(dob.getText()));
                pst.setInt(5, Integer.parseInt(age.getText()));
                pst.setString(6, gender.getSelectedItem().toString());
                pst.setString(7, email.getText());
                pst.setInt(8, Integer.parseInt(student_id.getText()));

                int rows = pst.executeUpdate();

                if(rows > 0) {
                    JOptionPane.showMessageDialog(f,"Student Updated Successfully");

                    student_id.setText("");
                    name.setText("");
                    department.setText("");
                    dob.setText("");
                    age.setText("");
                    email.setText("");
                    a_year.setSelectedIndex(0);
                    gender.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(f,"Student ID Not Found");
                }

                con.close();

            } catch(Exception ex){
                JOptionPane.showMessageDialog(f,"Error: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ===== FIELD STYLE =====
    private JTextField createField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 200, 30);
        field.setBackground(new Color(230,235,245));
        return field;
    }

    // ===== COMBO STYLE =====
    private void styleCombo(JComboBox<?> box) {
        box.setBackground(new Color(230,235,245));
    }

    // ===== BUTTON STYLE =====
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(52, 120, 246));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}