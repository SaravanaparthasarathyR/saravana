import javax.swing.*;
import java.awt.*;

public class UserDashboard {

    String username;

    UserDashboard(String username) {

        this.username = username;

        JFrame f = new JFrame("User Dashboard");
        f.setSize(900, 500);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // ===== BACKGROUND IMAGE =====
        ImageIcon bgIcon = new ImageIcon("sims.png");

        if (bgIcon.getIconWidth() == -1) {
            JOptionPane.showMessageDialog(f, "Image not found! Place sims.png in project folder.");
        }

        Image img = bgIcon.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(img));
        bgLabel.setBounds(0, 0, 900, 500);
        bgLabel.setLayout(null);

        f.setContentPane(bgLabel);

        // ===== MENU BAR =====
        JMenuBar menuBar = new JMenuBar();

        JMenu studentMenu = new JMenu("Student");
        JMenu systemMenu = new JMenu("System");
        JMenu aboutMenu = new JMenu("About");

        // ===== STUDENT MENU ITEMS =====
        JMenuItem searchItem = new JMenuItem("Search Student");
        JMenuItem viewMarkItem = new JMenuItem("View Mark");

        studentMenu.add(searchItem);
        studentMenu.add(viewMarkItem);

        // ===== SYSTEM MENU ITEMS =====
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem exitItem = new JMenuItem("Exit");

        systemMenu.add(logoutItem);
        systemMenu.addSeparator();
        systemMenu.add(exitItem);

        // ===== ABOUT MENU =====
        JMenuItem aboutItem = new JMenuItem("About");
        aboutMenu.add(aboutItem);

        menuBar.add(studentMenu);
        menuBar.add(systemMenu);
        menuBar.add(aboutMenu);

        f.setJMenuBar(menuBar);

        // ===== TITLE =====
        JLabel title = new JLabel("USER DASHBOARD", SwingConstants.CENTER);
        title.setBounds(0, 180, 900, 50);
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        bgLabel.add(title);

        // ===== ACTIONS =====

        // 🔥 FIXED HERE
        searchItem.addActionListener(e -> {
            f.dispose();
            new UserSearchStudent(username);
        });

        viewMarkItem.addActionListener(e -> {
            f.dispose();
            new ViewMark(username);
        });

        logoutItem.addActionListener(e -> {
            f.dispose();
            new LoginModule();
        });

        exitItem.addActionListener(e -> System.exit(0));

        aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(f, "Student Information Management System")
        );

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}