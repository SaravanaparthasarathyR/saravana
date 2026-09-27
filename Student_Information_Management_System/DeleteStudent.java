import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteStudent {

    DeleteStudent() {

        JFrame f = new JFrame("Delete Student");
        f.setSize(400,300);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // ===== DARK BACKGROUND =====
        f.getContentPane().setBackground(new Color(18, 32, 64));

        // ===== LABEL =====
        JLabel idLabel = new JLabel("Student ID");
        idLabel.setBounds(30,70,80,30);
        idLabel.setForeground(Color.WHITE);
        f.add(idLabel);

        // ===== TEXT FIELD =====
        JTextField student_id = new JTextField();
        student_id.setBounds(120,70,150,30);
        student_id.setBackground(new Color(230,235,245));
        f.add(student_id);

        // ===== BUTTONS =====
        JButton delete = new JButton("Delete");
        delete.setBounds(50,150,100,40);
        styleButton(delete);
        f.add(delete);

        JButton back = new JButton("Back");
        back.setBounds(200,150,100,40);
        styleButton(back);
        f.add(back);

        // ===== DELETE ACTION =====
        delete.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(f,
                    "Are you sure?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_OPTION) {

                try {
                    Connection con = DBConnection.getConnection();

                    String sql = "DELETE FROM students WHERE student_id=?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1,student_id.getText());

                    int rows = pst.executeUpdate();

                    if(rows > 0)
                        JOptionPane.showMessageDialog(f,"Student Deleted Successfully");
                    else
                        JOptionPane.showMessageDialog(f,"Student ID Not Found");

                    con.close();

                } catch(Exception ex){
                    JOptionPane.showMessageDialog(f,"Error: "+ex.getMessage());
                }
            }
        });

        // ===== BACK ACTION =====
        back.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ===== BUTTON STYLE =====
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(52, 120, 246));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}