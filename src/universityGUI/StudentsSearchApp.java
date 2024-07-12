package universityGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import universityCore.Students;
import universityDAO.StudentsDAO;

public class StudentsSearchApp extends JFrame {
    private StudentsDAO dao;
    JTable table;

    // launch app
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StudentsSearchApp stdSearch = new StudentsSearchApp();
                    stdSearch.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public StudentsSearchApp() {
        // create DAO
        try {
            dao = new StudentsDAO();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // ---- frame properties ----

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 500));
        // setResizable(false);
        setTitle("Search Students");

        // ---- frame objects ----

        // objects container
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
        mainPanel.setLayout(new BorderLayout());

        // top panel
        JPanel topSrcPanel = new JPanel();
        topSrcPanel.setMinimumSize(new Dimension(470, 40));
        topSrcPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        // top panel label
        JLabel label = new JLabel();
        label.setText("Search by name: ");
        label.setHorizontalAlignment(JLabel.LEFT);
        topSrcPanel.add(label);

        // top panel text field
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(250, 25));
        topSrcPanel.add(searchTextField);

        // table
        table = new JTable();

        // top panel search button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // -- search action --

                // A. search for student name inputed and print student(s) data if textField is
                // not empty
                // B. print all data if textField is empty
                try {
                    String name = searchTextField.getText();
                    List<Students> students = null;

                    if (name != null && name.trim().length() > 0) {
                        students = dao.searchStudents(name);
                    } else {
                        students = dao.getAllStudents();
                    }

                    // create model and update table
                    StudentsTableModel model = new StudentsTableModel(students);
                    table.setModel(model);

                    /*
                     * for (Students temp : students) {
                     * System.out.println(temp);
                     * }
                     */

                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(StudentsSearchApp.this, "Error: " + exc);
                }
            }
        });
        topSrcPanel.add(searchButton);

        // -- bottom panel --

        // add button
        JButton addStdButton = new JButton("Register Student");
        addStdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // pop dialog
                AddStudentDialog dialog = new AddStudentDialog(StudentsSearchApp.this, dao);
                dialog.setVisible(true);
            }
        });

        JPanel bttnPanel = new JPanel();
        bttnPanel.add(addStdButton);

        // ---- add frame objects ----

        mainPanel.add(topSrcPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(bttnPanel, BorderLayout.SOUTH);
        add(mainPanel); // objects container

    }

    public void refreshStudentList() {
        try {
            List<Students> students = dao.getAllStudents();

            // create model and update list
            StudentsTableModel model = new StudentsTableModel(students);

            table.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
