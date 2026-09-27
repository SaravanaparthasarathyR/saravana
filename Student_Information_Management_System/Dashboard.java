import javax.swing.*;
import java.awt.*;

public class Dashboard {

    Dashboard() {

        JFrame f = new JFrame("Dashboard");
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
        JMenu markMenu = new JMenu("Mark");
        JMenu systemMenu = new JMenu("System");
        JMenu helpMenu = new JMenu("Help");

        // ===========================
        // STUDENT MENU ITEMS
        // ===========================
        JMenuItem addItem = new JMenuItem("Add Student");
        JMenuItem viewItem = new JMenuItem("View Students");
        JMenuItem updateItem = new JMenuItem("Update Student");
        JMenuItem deleteItem = new JMenuItem("Delete Student");
        JMenuItem searchItem = new JMenuItem("Search Student");
        JMenuItem bulkAddStudentItem = new JMenuItem("Bulk Add Students");

        studentMenu.add(addItem);
        studentMenu.add(viewItem);
        studentMenu.add(updateItem);
        studentMenu.add(deleteItem);
        studentMenu.add(searchItem);
        studentMenu.addSeparator();
        studentMenu.add(bulkAddStudentItem);

        // ===========================
        // MARK MENU ITEMS
        // ===========================
        JMenuItem addMarkItem = new JMenuItem("Add Mark");
        JMenuItem bulkAddMarkItem = new JMenuItem("Bulk Add Marks");
        JMenuItem viewMarksItem = new JMenuItem("View All Marks");
        JMenuItem updateMarkItem = new JMenuItem("Update Mark");
        JMenuItem searchMarkItem = new JMenuItem("Search Mark");

        markMenu.add(addMarkItem);
        markMenu.add(bulkAddMarkItem);
        markMenu.addSeparator();
        markMenu.add(viewMarksItem);
        markMenu.add(updateMarkItem);
        markMenu.add(searchMarkItem);

        // ===========================
        // SYSTEM MENU
        // ===========================
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem exitItem = new JMenuItem("Exit");

        systemMenu.add(logoutItem);
        systemMenu.addSeparator();
        systemMenu.add(exitItem);

        // ===========================
        // HELP MENU
        // ===========================
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        // ===========================
        // ADD MENUS
        // ===========================
        menuBar.add(studentMenu);
        menuBar.add(markMenu);
        menuBar.add(systemMenu);
        menuBar.add(helpMenu);

        f.setJMenuBar(menuBar);

        // ===========================
        // TITLE
        // ===========================
        JLabel title = new JLabel("ADMIN LOGIN", SwingConstants.CENTER);
        title.setBounds(0, 180, 900, 50);
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Segoe UI", Font.BOLD, 58));
        bgLabel.add(title);

        // ===========================
        // STUDENT ACTIONS
        // ===========================
        addItem.addActionListener(e -> {
            f.dispose();
            new AddStudent();
        });

        viewItem.addActionListener(e -> {
            f.dispose();
            new ViewStudent();
        });

        updateItem.addActionListener(e -> {
            f.dispose();
            new UpdateStudent();
        });

        deleteItem.addActionListener(e -> {
            f.dispose();
            new DeleteStudent();
        });

        searchItem.addActionListener(e -> {
            f.dispose();
            new SearchStudent();
        });

        bulkAddStudentItem.addActionListener(e -> {
            f.dispose();
            new BulkAddStudent();
        });

        // ===========================
        // MARK ACTIONS
        // ===========================
        addMarkItem.addActionListener(e -> {
            f.dispose();
            new AddMark();
        });

        bulkAddMarkItem.addActionListener(e -> {
            f.dispose();
            new BulkAddMarks();
        });

        viewMarksItem.addActionListener(e -> {
            f.dispose();
            new AdminViewMarks();
        });

        updateMarkItem.addActionListener(e -> {
            f.dispose();
            new UpdateMark();
        });

        searchMarkItem.addActionListener(e -> {
            f.dispose();
            new SearchMark();
        });

        // ===========================
        // SYSTEM ACTIONS
        // ===========================
        logoutItem.addActionListener(e -> {
            f.dispose();
            new LoginModule();
        });

        exitItem.addActionListener(e -> System.exit(0));

        // ===========================
        // ABOUT
        // ===========================
        aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        f,
                        "Student Information Management System\nDeveloped using Java Swing and MS Access."
                )
        );

        // ===========================
        // FINAL
        // ===========================
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}