package universityGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import universityCore.Professors;
import universityCore.Students;
import universityDAO.ProfessorsDAO;
import universityDAO.StudentsDAO;
import universityGUI.professorsGUI.ProfessorsManagementDialog;
import universityGUI.professorsGUI.ProfessorsTableModel;
import universityGUI.studentsGUI.StudentEnrollmentDialog;
import universityGUI.studentsGUI.StudentManagementDialog;
import universityGUI.studentsGUI.StudentsTableModel;

public class StudentFacultyManagementGUI extends JFrame {
    private StudentsDAO stDAO;
    private ProfessorsDAO prDAO;
    private char entity;
    private int currentUser;
    private JTable table;
    private Font segoePlain = new Font("Segoe UI", Font.PLAIN, 12);
    private Font segoeBold = new Font("Segoe UI", Font.BOLD, 12);

    public StudentFacultyManagementGUI(int currentUser, char entity) {
        this.entity = entity;
        this.currentUser = currentUser;

        // create DAO
        if (entity == 's') {
            try {
                stDAO = new StudentsDAO();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                prDAO = new ProfessorsDAO();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // ---- frame properties ----

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(250, 100);
        setMinimumSize(new Dimension(850, 600));
        // setResizable(false);
        if (entity == 's') {
            setTitle("Students Management Panel");
        } else {
            setTitle("Professors Management Panel");

        }

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
                String name = searchTextField.getText();
                searchTable(name);
            }
        });
        northSrcPanel.add(searchButton);

        // -- bottom panel --

        // add button
        JButton addStdButton = new JButton("Register");
        addStdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRegister();
            }
        });

        // update button
        JButton updStdButton = new JButton("Update");
        updStdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRegister();
            }
        });

        // delete button
        JButton delButton = new JButton("Delete");
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRegister();
            }
        });

        // enrollments button
        JButton enrButton = new JButton("Add/Check Enrollments");
        if (entity == 's') {
            enrButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int row = table.getSelectedRow(); // get selected row

                        // check if row is selected
                        if (row < 0) {
                            JOptionPane.showMessageDialog(StudentFacultyManagementGUI.this, "Select a student to manage enrollments.", "Error", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        // get students object
                        Students theStudents = (Students) table.getValueAt(row, StudentsTableModel.OBJECT_COL);

                        // pop enrollments dialog
                        StudentEnrollmentDialog enrDialog = new StudentEnrollmentDialog(theStudents.getMatr());
                        enrDialog.setVisible(true);
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(StudentFacultyManagementGUI.this, "Error: " + exc, "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // log button
        JButton logButton = new JButton("Log");
        logButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AuditLogDialog dialog = new AuditLogDialog(StudentFacultyManagementGUI.this, currentUser, entity);
                    dialog.setVisible(true);
                } catch (Exception exc) {
                    JOptionPane.showConfirmDialog(StudentFacultyManagementGUI.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // button panel
        JPanel bttnPanel = new JPanel();
        bttnPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        bttnPanel.add(addStdButton);
        bttnPanel.add(updStdButton);
        bttnPanel.add(delButton);
        if (entity == 's') {
            bttnPanel.add(enrButton);
        }
        bttnPanel.add(logButton);

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

    /* ---- Buttons Actions ---- */

    /* -- Search -- */
    public void searchTable(String theName) {
        if (entity == 's') {
            try {
                List<Students> students = null;

                if (theName != null && theName.trim().length() > 0) {
                    students = stDAO.searchStudents(theName);
                } else {
                    students = stDAO.getAllStudents();
                }

                // create model and update table
                StudentsTableModel model = new StudentsTableModel(students);
                table.setModel(model);

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(StudentFacultyManagementGUI.this, "Error: " + exc);
            }
        } else {
            try {
                List<Professors> professors = null;

                if (theName != null && theName.trim().length() > 0) {
                    professors = prDAO.searchProfessors(theName);
                } else {
                    professors = prDAO.getAllProfessors();
                }

                // create model and update table
                ProfessorsTableModel model = new ProfessorsTableModel(professors);
                table.setModel(model);

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(StudentFacultyManagementGUI.this, "Error: " + exc);
            }
        }
    }

    /* -- Add -- */
    public void addRegister() {
        // pop dialog
        if (entity == 's') {
            StudentManagementDialog dialog = new StudentManagementDialog(StudentFacultyManagementGUI.this, stDAO, null, false, currentUser);
            dialog.setVisible(true);
        } else {
            ProfessorsManagementDialog dialog = new ProfessorsManagementDialog(StudentFacultyManagementGUI.this, prDAO, null, false, currentUser);
            dialog.setVisible(true);
        }
    }

    /* -- Update -- */
    public void updateRegister() {
        // get selected row
        int row = table.getSelectedRow();

        // check if row is selected
        if (row < 0) {
            JOptionPane.showMessageDialog(StudentFacultyManagementGUI.this, "Select a row to update", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (entity == 's') {
            // get data from selected row
            Students tempStudents = (Students) table.getValueAt(row, StudentsTableModel.OBJECT_COL);

            // pop update window
            StudentManagementDialog dialog = new StudentManagementDialog(StudentFacultyManagementGUI.this, stDAO, tempStudents, true, currentUser);
            dialog.setVisible(true);
        } else {
            // get data from selected row
            Professors tempProfessors = (Professors) table.getValueAt(row, ProfessorsTableModel.OBJECT_COL);

            // pop update window
            ProfessorsManagementDialog dialog = new ProfessorsManagementDialog(StudentFacultyManagementGUI.this, prDAO, tempProfessors, true, currentUser);
            dialog.setVisible(true);
        }
    }

    /* -- Delete -- */
    public void deleteRegister() {
        try {
            // get selected row
            int row = table.getSelectedRow();

            // check if row is selected
            if (row < 0) {
                JOptionPane.showMessageDialog(StudentFacultyManagementGUI.this, "Select a row to delete", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (entity == 's') {

                // get data from selected row
                Students tempStudents = (Students) table.getValueAt(row, StudentsTableModel.OBJECT_COL);

                // promp confirm window
                int confirm = JOptionPane.showConfirmDialog(StudentFacultyManagementGUI.this,
                        "Are you sure you want to delete?", getTitle(),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // send student id to DAO for deletion
                stDAO.deleteStudent(tempStudents.getMatr());
            } else {
                // get data from selected row
                Professors temProfessors = (Professors) table.getValueAt(row, ProfessorsTableModel.OBJECT_COL);

                // promp confirm window
                int confirm = JOptionPane.showConfirmDialog(StudentFacultyManagementGUI.this, "Are you sure you want to delete " + 
                    temProfessors.getName() + "?", getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // sent professor id to DAO for deletion
                prDAO.deleteProfessors(temProfessors.getMatr());
            }

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(StudentFacultyManagementGUI.this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        // refresh list
        refreshTable();
    }

    public void refreshTable() {
        try {
            if (entity == 's') {
                List<Students> students = stDAO.getAllStudents();

                // create model and update list
                StudentsTableModel model = new StudentsTableModel(students);

                table.setModel(model);
            } else {
                List<Professors> professors = prDAO.getAllProfessors();

                // create model and update list
                ProfessorsTableModel model = new ProfessorsTableModel(professors);

                table.setModel(model);
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
