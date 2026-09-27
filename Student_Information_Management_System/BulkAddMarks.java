import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BulkAddMarks {

    JFrame f;
    JTable table;
    DefaultTableModel model;
    Connection conn;

    BulkAddMarks() {

        f = new JFrame("Bulk Add Marks");
        f.setSize(900,500);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());

        // ===== DATABASE =====
        try{
            String url="jdbc:ucanaccess://E:/tech/studentdb.accdb";
            conn=DriverManager.getConnection(url);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(f,e.getMessage());
        }

        // ===== TABLE =====
        String columns[]={
                "Student Gmail",
                "Java",
                "Python",
                "C++",
                "AI",
                "DBMS"
        };

        model=new DefaultTableModel(columns,10);

        table=new JTable(model);

        JScrollPane scroll=new JScrollPane(table);

        f.add(scroll,BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel p=new JPanel();

        JButton addRow=new JButton("Add Row");
        JButton save=new JButton("Save All");
        JButton back=new JButton("Back");

        p.add(addRow);
        p.add(save);
        p.add(back);

        f.add(p,BorderLayout.SOUTH);

        // ===== ADD ROW =====
        addRow.addActionListener(e->{

            model.addRow(new Object[]{"","","","","",""});

        });

        // ===== SAVE =====
        save.addActionListener(e->{

            try{

                String sql="INSERT INTO Marks VALUES(?,?,?,?,?,?)";

                PreparedStatement pst=conn.prepareStatement(sql);

                int count=0;

                for(int i=0;i<model.getRowCount();i++){

                    Object email=model.getValueAt(i,0);

                    if(email==null || email.toString().trim().isEmpty())
                        continue;

                    pst.setString(1,email.toString());
                    pst.setInt(2,Integer.parseInt(model.getValueAt(i,1).toString()));
                    pst.setInt(3,Integer.parseInt(model.getValueAt(i,2).toString()));
                    pst.setInt(4,Integer.parseInt(model.getValueAt(i,3).toString()));
                    pst.setInt(5,Integer.parseInt(model.getValueAt(i,4).toString()));
                    pst.setInt(6,Integer.parseInt(model.getValueAt(i,5).toString()));

                    pst.executeUpdate();

                    count++;

                }

                JOptionPane.showMessageDialog(f,count+" Students Marks Added Successfully");

                model.setRowCount(0);

                for(int i=0;i<10;i++)
                    model.addRow(new Object[]{"","","","","",""});

            }
            catch(Exception ex){

                JOptionPane.showMessageDialog(f,ex.getMessage());

            }

        });

        // ===== BACK =====
        back.addActionListener(e->{

            f.dispose();

            new Dashboard();

        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}