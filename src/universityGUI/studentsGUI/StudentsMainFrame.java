package universityGUI.studentsGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import universityCore.Students;
import universityDAO.StudentsDAO;

public class StudentsMainFrame extends JFrame {
    private StudentsDAO dao;
    JTable table;
    Font segoePlain = new Font("Segoe UI", Font.PLAIN, 12);
    Font segoeBold = new Font("Segoe UI", Font.BOLD, 12);

    // launch app
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StudentsMainFrame stdSearch = new StudentsMainFrame();
                    stdSearch.setVisible(true);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });
    }

    public StudentsMainFrame() {
        // create DAO
        try {
            dao = new StudentsDAO();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // ---- frame properties ----

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(250, 100);
        setMinimumSize(new Dimension(850, 600));
        // setResizable(false);
        setTitle("Search Students");

        // ---- frame objects ----

        // objects container
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
        mainPanel.setLayout(new BorderLayout());

        // top panel
        JPanel northSrcPanel = new JPanel();
        northSrcPanel.setMinimumSize(new Dimension(470, 40));
        northSrcPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        // top panel label
        JLabel label = new JLabel();
        label.setText("Search by name: ");
        label.setHorizontalAlignment(JLabel.LEFT);
        northSrcPanel.add(label);

        // top panel text field
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(250, 25));
        northSrcPanel.add(searchTextField);

        // table
        table = new JTable();
        table.setRowHeight(25);
        table.setFont(segoePlain);
        table.setShowHorizontalLines(false);
        // table header
        table.getTableHeader().setBackground(new Color(59, 70, 102));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(segoeBold);

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
                    JOptionPane.showMessageDialog(StudentsMainFrame.this, "Error: " + exc);
                }
            }
        });
        northSrcPanel.add(searchButton);

        // -- bottom panel --

        // add button
        JButton addStdButton = new JButton("Register Student");
        addStdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // pop dialog
                StudentDialog dialog = new StudentDialog(StudentsMainFrame.this, dao, null, false);
                dialog.setVisible(true);
            }
        });

        // update button
        JButton updStdButton = new JButton("Update Student");
        updStdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get selected row
                int row = table.getSelectedRow();

                // check if row is selected
                if (row < 0) {
                    JOptionPane.showMessageDialog(StudentsMainFrame.this, "Select a student to update", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // get data from selected row
                Students tempStudents = (Students) table.getValueAt(row, StudentsTableModel.OBJECT_COL);

                // pop update window
                StudentDialog dialog = new StudentDialog(StudentsMainFrame.this, dao, tempStudents, true);
                dialog.setVisible(true);

            }
        });

        // delete button
        JButton delButton = new JButton("Delete student");
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // get selected row
                    int row = table.getSelectedRow();

                    // check if row is selected
                    if (row < 0) {
                        JOptionPane.showMessageDialog(StudentsMainFrame.this, "Select a student to delete", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // get data from selected row
                    Students tempStudents = (Students) table.getValueAt(row, StudentsTableModel.OBJECT_COL);

                    // promp confirm window
                    int confirm = JOptionPane.showConfirmDialog(StudentsMainFrame.this,
                            "Are you sure you want to delete", getTitle(),
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirm != JOptionPane.YES_OPTION) {
                        return;
                    }

                    // send student id to DAO for deletion
                    dao.deleteStudent(tempStudents.getMatr());

                    // refresh list
                    refreshStudentList();

                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(StudentsMainFrame.this, "Error: " + exc, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // enrollments button
        JButton enrButton = new JButton("Add/Check Enrollments");
        enrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow(); // get selected row

                    // check if row is selected
                    if (row < 0) {
                        JOptionPane.showMessageDialog(StudentsMainFrame.this, "Select a student to manage enrollments.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // get students object
                    Students theStudents = (Students) table.getValueAt(row, StudentsTableModel.OBJECT_COL);

                    // pop enrollments dialog
                    StudentEnrollmentDialog enrDialog = new StudentEnrollmentDialog(theStudents.getMatr());
                    enrDialog.setVisible(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(StudentsMainFrame.this, "Error: " + exc, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // button panel
        JPanel bttnPanel = new JPanel();
        bttnPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        bttnPanel.add(addStdButton);
        bttnPanel.add(updStdButton);
        bttnPanel.add(delButton);
        bttnPanel.add(enrButton);

        // east panel properties
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // eastPanel.setBackground(Color.BLACK);
        eastPanel.add(bttnPanel);

        // scroll panel
        JScrollPane centerPanel = new JScrollPane(table);

        // ---- add frame objects ----

        mainPanel.add(northSrcPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(eastPanel, BorderLayout.SOUTH);
        add(mainPanel); // objects container

    }

    public void refreshStudentList() {
        try {
            List<Students> students = dao.getAllStudents();

            // create model and update list
            StudentsTableModel model = new StudentsTableModel(students);

            table.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
