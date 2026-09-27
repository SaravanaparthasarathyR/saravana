import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BulkAddStudent {

    JFrame f;
    JTable table;
    DefaultTableModel model;

    BulkAddStudent() {

        f = new JFrame("Bulk Add Students");
        f.setSize(1000, 500);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        String[] columns = {
                "Student ID",
                "Name",
                "Department",
                "Year",
                "DOB",
                "Age",
                "Gender",
                "Email"
        };

        model = new DefaultTableModel(columns, 0);

        // Add 10 empty rows
        for (int i = 0; i < 10; i++) {
            model.addRow(new Object[]{
                    "", "", "", "Select", "", "", "Select", ""
            });
        }

        table = new JTable(model);

        // ===== Year ComboBox =====
        JComboBox<String> yearCombo = new JComboBox<>(new String[]{
                "Select", "1st", "2nd", "3rd", "4th"
        });
        table.getColumnModel().getColumn(3)
                .setCellEditor(new DefaultCellEditor(yearCombo));

        // ===== Gender ComboBox =====
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{
                "Select", "Male", "Female", "Other"
        });
        table.getColumnModel().getColumn(6)
                .setCellEditor(new DefaultCellEditor(genderCombo));

        JScrollPane scroll = new JScrollPane(table);
        f.add(scroll, BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton addRow = new JButton("Add Row");
        JButton save = new JButton("Save All");
        JButton back = new JButton("Back");

        panel.add(addRow);
        panel.add(save);
        panel.add(back);

        f.add(panel, BorderLayout.SOUTH);

        // ===== Add New Row =====
        addRow.addActionListener(e -> {
            model.addRow(new Object[]{
                    "", "", "", "Select", "", "", "Select", ""
            });
        });

        // ===== Save All =====
        save.addActionListener(e -> {

            String sql = "INSERT INTO students(student_id,name,department,a_year,dob,age,gender,email) VALUES(?,?,?,?,?,?,?,?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                int count = 0;

                for (int i = 0; i < model.getRowCount(); i++) {

                    Object id = model.getValueAt(i, 0);

                    if (id == null || id.toString().trim().isEmpty())
                        continue;

                    pst.setString(1, model.getValueAt(i, 0).toString());
                    pst.setString(2, model.getValueAt(i, 1).toString());
                    pst.setString(3, model.getValueAt(i, 2).toString());
                    pst.setString(4, model.getValueAt(i, 3).toString());
                    pst.setString(5, model.getValueAt(i, 4).toString());
                    pst.setInt(6, Integer.parseInt(model.getValueAt(i, 5).toString()));
                    pst.setString(7, model.getValueAt(i, 6).toString());
                    pst.setString(8, model.getValueAt(i, 7).toString());

                    pst.addBatch();
                    count++;
                }

                pst.executeBatch();

                JOptionPane.showMessageDialog(f,
                        count + " Students Added Successfully!");

                // Clear Table
                model.setRowCount(0);

                for (int i = 0; i < 10; i++) {
                    model.addRow(new Object[]{
                            "", "", "", "Select", "", "", "Select", ""
                    });
                }

            } catch (SQLIntegrityConstraintViolationException ex) {

                JOptionPane.showMessageDialog(f,
                        "Duplicate Student ID Found!");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(f,
                        "Error : " + ex.getMessage());
            }

        });

        // ===== Back =====
        back.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}