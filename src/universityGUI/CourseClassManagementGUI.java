package universityGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import universityCore.Classes;
import universityCore.Courses;
import universityDAO.ClassesDAO;
import universityDAO.CoursesDAO;
import universityGUI.classesGUI.ClassesManagementDialog;
import universityGUI.classesGUI.ClassesTableModel;
import universityGUI.coursesGUI.CoursesManagementDialog;
import universityGUI.coursesGUI.CoursesTableModel;

public class CourseClassManagementGUI extends JFrame implements FontsAndColors {
    private int currentUser;
    private char entity;
    private ClassesDAO clDAO;
    private CoursesDAO coDAO;
    private JTable table;
    
    public CourseClassManagementGUI(int currentUser, char entity){
        this(entity);
        this.currentUser = currentUser;
        this.entity = entity;
        if (entity == 'l') {
            setTitle("Class Manager");
        } else {
            setTitle("Course Manager");
        }
    }

    public CourseClassManagementGUI(char entity) {
        // create DAOs
        try {
            clDAO = new ClassesDAO();
            coDAO = new CoursesDAO();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(CourseClassManagementGUI.this, "Error while connecting to the database.\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }

        /* ---- Properties ---- */
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /* ---- Panels ---- */
        JPanel mainPanel = new JPanel();

        JPanel eastPanel = new JPanel();
        JPanel bttnPanel = new JPanel();
        JScrollPane tablePanel;

        // -- main panel --
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // -- east panel --
        eastPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        eastPanel.setBorder(new EmptyBorder(0, 5, 0, 0));

        // - buttons panel -
        bttnPanel.setLayout(new GridLayout(3, 1, 0, 10));

        // buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // add
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRegister();
            }
        });

        // update
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRegister();
            }
        });

        // delete
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRegister();
            }
        });

        // -- table panel --

        // - table -
        table = new JTable();
        table.getTableHeader().setBackground(SECONDARY_C);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(SEGOE_BOLD);
        table.setFont(SEGOE_PLAIN);
        table.setRowHeight(25);
        table.setShowHorizontalLines(false);
        // fill table
        try {
            if (entity == 'l') { // 'l' = c[l]asses
                // get all classes from the db
                List<Classes> classes = clDAO.getAllClasses();
                // create table model
                ClassesTableModel model = new ClassesTableModel(classes);
                
                table.setModel(model);
            } else {
                // get all courses from the db
                List<Courses> courses = coDAO.getAllCourses();
                // create table model
                CoursesTableModel model = new CoursesTableModel(courses);

                table.setModel(model);
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(CourseClassManagementGUI.this, "Error while filling the table.\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }

        tablePanel = new JScrollPane(table);

        /* ---- Add components to the frame ---- */

        bttnPanel.add(addButton);
        bttnPanel.add(updateButton);
        bttnPanel.add(deleteButton);

        eastPanel.add(bttnPanel);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(eastPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    /* ---- Buttons Events ---- */

    // add action
    private void addRegister() {
        if (entity == 'l') { // 'l' = c[l]ass
            ClassesManagementDialog dialog = new ClassesManagementDialog(CourseClassManagementGUI.this, clDAO, null, false);
            dialog.setVisible(true);
        } else {
            CoursesManagementDialog dialog = new CoursesManagementDialog(CourseClassManagementGUI.this, coDAO, null, false);
            dialog.setVisible(true);
        }
    }

    // update action
    private void updateRegister() {
        // get selected row
        int row = table.getSelectedRow();
        // check if row is selected
        if (row < 0) {
            JOptionPane.showMessageDialog(CourseClassManagementGUI.this, "You must select a row to update", getTitle(), JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (entity == 'l') { // 'l' = c[l]ass
                // get data from selected row
                Classes tempClasses = (Classes) table.getValueAt(row, ClassesTableModel.OBJECT_COL);

                ClassesManagementDialog dialog = new ClassesManagementDialog(CourseClassManagementGUI.this, clDAO, tempClasses, true);
                dialog.setVisible(true);
            } else {
                // get data from selected row
                Courses tempCourses = (Courses) table.getValueAt(row, CoursesTableModel.OBJECT_COL);

                CoursesManagementDialog dialog = new CoursesManagementDialog(CourseClassManagementGUI.this, coDAO, tempCourses, true);
                dialog.setVisible(true);
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(CourseClassManagementGUI.this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // delete action
    private void deleteRegister() {
        // get selected row
        int row = table.getSelectedRow();
        // check if row is selected
        if (row < 0) {
            JOptionPane.showMessageDialog(CourseClassManagementGUI.this, "You must select a row to delete", getTitle(), JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (entity == 'l') { // 'l' = c[l]asses
                // get data from selected row
                Classes tempClasses = (Classes) table.getValueAt(row, ClassesTableModel.OBJECT_COL);

                // promp confirm window
                int confirm = JOptionPane.showConfirmDialog(CourseClassManagementGUI.this, "Are you sure you want to delete?", getTitle(),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // send class id to DAO for deletion
                clDAO.deleteClass(tempClasses.getIdClass());
            } else {
                // get data from selected row
                Courses tempCourses = (Courses) table.getValueAt(row, CoursesTableModel.OBJECT_COL);

                // promp confirm window
                int confirm = JOptionPane.showConfirmDialog(CourseClassManagementGUI.this, "Are you sure you want to delete?", getTitle(),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // send course id to DAO for deletion
                coDAO.deleteCourse(tempCourses.getIdCourse());
            }
            refreshTable();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(CourseClassManagementGUI.this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // refresh table
    public void refreshTable() {
        try {
            if (entity == 'l') {
                List<Classes> classes = clDAO.getAllClasses();

                // create model and update list
                ClassesTableModel model = new ClassesTableModel(classes);

                table.setModel(model);
            } else {
                List<Courses> courses = coDAO.getAllCourses();

                // create model and update list
                CoursesTableModel model = new CoursesTableModel(courses);

                table.setModel(model);
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error:\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
