import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginModule {

    private JFrame frame;
    private Connection conn;

    public LoginModule() {

        // ===== DATABASE CONNECTION =====
        try {
            String dbPath = "E:/tech/studentdb.accdb";
            String url = "jdbc:ucanaccess://" + dbPath;
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "DB Error: " + e.getMessage());
            return;
        }

        // ===== FRAME =====
        frame = new JFrame("Login");
        frame.setSize(900, 500);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(new Color(18, 32, 63));

        // ===== MAIN CARD =====
        JPanel card = new JPanel();
        card.setBounds(100, 50, 700, 350);
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),2));
        frame.add(card);

        // ===== LEFT IMAGE =====
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, 350, 350);

        ImageIcon icon = new ImageIcon("F:/tech/bg.png"); // your image path
        Image img = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));

        card.add(imageLabel);

        // ===== TITLE =====
        JLabel title = new JLabel("Log In");
        title.setBounds(470, 40, 100, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(title);

        // ===== USERNAME =====
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(400, 90, 100, 20);
        card.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(400, 110, 220, 30);
        card.add(userField);

        // ===== PASSWORD =====
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(400, 150, 100, 20);
        card.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(400, 170, 220, 30);
        card.add(passField);

        // ===== LOGIN BUTTON =====
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(400, 220, 220, 35);
        loginBtn.setBackground(new Color(66, 133, 244));
        loginBtn.setForeground(Color.WHITE);
        card.add(loginBtn);

        // ===== ADD USER =====
        JButton addUserBtn = new JButton("Add User");
        addUserBtn.setBounds(400, 270, 220, 30);
        card.add(addUserBtn);

        // ===== FORGOT PASSWORD =====
        JButton forgotBtn = new JButton("Forgot Password?");
        forgotBtn.setBounds(400, 310, 220, 25);
        forgotBtn.setBorderPainted(false);
        forgotBtn.setForeground(Color.BLUE);
        forgotBtn.setBackground(Color.WHITE);
        card.add(forgotBtn);

        // ===== LOGIN ACTION =====
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            try {
                if (username.equals("admin") && password.equals("1234")) {
                    JOptionPane.showMessageDialog(frame, "Admin Login");
                    frame.dispose();
                    new Dashboard();
                } else if (validateUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Welcome " + username);
                    frame.dispose();
                    new UserDashboard(username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Login");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        // ===== ADD USER ACTION =====
        addUserBtn.addActionListener(e -> {
            JTextField newUser = new JTextField();
            JPasswordField newPass = new JPasswordField();

            Object[] msg = {
                    "Username:", newUser,
                    "Password:", newPass
            };

            int option = JOptionPane.showConfirmDialog(frame, msg, "Add User", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String u = newUser.getText().trim();
                String p = new String(newPass.getPassword());

                if (addUserToDB(u, p)) {
                    JOptionPane.showMessageDialog(frame, "User added");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error adding user");
                }
            }
        });

        // ===== FORGOT PASSWORD ACTION =====
        forgotBtn.addActionListener(e -> {

            String username = JOptionPane.showInputDialog(frame, "Enter Username:");

            if (username != null && !username.trim().isEmpty()) {

                try {
                    String password = getPasswordFromDB(username);

                    if (password != null) {
                        JOptionPane.showMessageDialog(frame,
                                "Your Password is: " + password);
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "User not found!");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ===== ADD USER METHOD =====
    private boolean addUserToDB(String u, String p) {
        try {
            String sql = "INSERT INTO Users VALUES (?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, u);
            pst.setString(2, p);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===== VALIDATE USER =====
    private boolean validateUser(String u, String p) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Username=? AND Password=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, u);
        pst.setString(2, p);
        return pst.executeQuery().next();
    }

    // ===== GET PASSWORD =====
    private String getPasswordFromDB(String username) throws SQLException {

        String sql = "SELECT Password FROM Users WHERE Username=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, username);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getString("Password");
        }

        return null;
    }

    
}