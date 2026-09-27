import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddStudent {

    AddStudent() {

        JFrame f = new JFrame("Add Student");
        f.setSize(500, 600);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // ===== DARK BACKGROUND =====
        f.getContentPane().setBackground(new Color(18, 32, 64));

        // ===== TITLE =====
        JLabel title = new JLabel("Add Student");
        title.setBounds(170, 10, 200, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        f.add(title);

        // Common label color
        Color labelColor = Color.WHITE;

        // ===== LABELS & FIELDS =====
        JLabel l1 = new JLabel("Student ID");
        l1.setBounds(50, 50, 100, 30);
        l1.setForeground(labelColor);
        f.add(l1);

        JTextField id = createField(150, 50);
        f.add(id);

        JLabel l2 = new JLabel("Name");
        l2.setBounds(50, 100, 100, 30);
        l2.setForeground(labelColor);
        f.add(l2);

        JTextField name = createField(150, 100);
        f.add(name);

        JLabel l3 = new JLabel("Department");
        l3.setBounds(50, 150, 100, 30);
        l3.setForeground(labelColor);
        f.add(l3);

        JTextField dept = createField(150, 150);
        f.add(dept);

        JLabel l4 = new JLabel("Year");
        l4.setBounds(50, 200, 100, 30);
        l4.setForeground(labelColor);
        f.add(l4);

        JComboBox<String> year = new JComboBox<>(
                new String[]{"Select", "1st", "2nd", "3rd", "4th"});
        year.setBounds(150, 200, 200, 30);
        styleCombo(year);
        f.add(year);

        JLabel l5 = new JLabel("DOB");
        l5.setBounds(50, 250, 100, 30);
        l5.setForeground(labelColor);
        f.add(l5);

        JTextField dob = createField(150, 250);
        f.add(dob);

        JLabel l6 = new JLabel("Age");
        l6.setBounds(50, 300, 100, 30);
        l6.setForeground(labelColor);
        f.add(l6);

        JTextField age = createField(150, 300);
        f.add(age);

        JLabel l7 = new JLabel("Gender");
        l7.setBounds(50, 350, 100, 30);
        l7.setForeground(labelColor);
        f.add(l7);

        JComboBox<String> gender = new JComboBox<>(
                new String[]{"Select", "Male", "Female", "Other"});
        gender.setBounds(150, 350, 200, 30);
        styleCombo(gender);
        f.add(gender);

        JLabel l8 = new JLabel("Email");
        l8.setBounds(50, 400, 100, 30);
        l8.setForeground(labelColor);
        f.add(l8);

        JTextField email = createField(150, 400);
        f.add(email);

        // ===== BUTTONS =====
        JButton addBtn = new JButton("Add");
        addBtn.setBounds(80, 470, 120, 40);
        styleButton(addBtn);
        f.add(addBtn);

        JButton back = new JButton("Back");
        back.setBounds(250, 470, 120, 40);
        styleButton(back);
        f.add(back);

        // ===== ACTIONS =====
        addBtn.addActionListener(e -> {

            if (id.getText().trim().isEmpty() ||
                    name.getText().trim().isEmpty() ||
                    dept.getText().trim().isEmpty() ||
                    dob.getText().trim().isEmpty() ||
                    age.getText().trim().isEmpty() ||
                    email.getText().trim().isEmpty() ||
                    year.getSelectedIndex() == 0 ||
                    gender.getSelectedIndex() == 0) {

                JOptionPane.showMessageDialog(f, "Please fill all fields correctly!");
                return;
            }

            int studentAge;

            try {
                studentAge = Integer.parseInt(age.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(f, "Enter valid numeric age!");
                return;
            }

            String sql = "INSERT INTO students " +
                    "(student_id, name, department, a_year, dob, age, gender, email) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setString(1, id.getText().trim());
                pst.setString(2, name.getText().trim());
                pst.setString(3, dept.getText().trim());
                pst.setString(4, year.getSelectedItem().toString());
                pst.setString(5, dob.getText().trim());
                pst.setInt(6, studentAge);
                pst.setString(7, gender.getSelectedItem().toString());
                pst.setString(8, email.getText().trim());

                pst.executeUpdate();

                JOptionPane.showMessageDialog(f, "Student Added Successfully!");

                id.setText(""); name.setText(""); dept.setText("");
                dob.setText(""); age.setText(""); email.setText("");
                year.setSelectedIndex(0); gender.setSelectedIndex(0);

            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(f, "Student ID already exists!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Database Error: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ===== TEXT FIELD STYLE =====
    private JTextField createField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 200, 30);
        field.setBackground(new Color(230, 235, 245));
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return field;
    }

    // ===== COMBO STYLE =====
    private void styleCombo(JComboBox<?> box) {
        box.setBackground(new Color(230, 235, 245));
    }

    // ===== BUTTON STYLE =====
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(52, 120, 246));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}