package universityGUI.classesGUI;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import universityCore.Classes;
import universityCore.Courses;
import universityDAO.ClassesDAO;
import universityDAO.CoursesDAO;
import universityGUI.CourseClassManagementGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClassesManagementDialog extends JDialog {

    private CourseClassManagementGUI managementMainFrame;
    private ClassesDAO clDAO;
    private Classes previousClasses;
    private boolean updateMode;

    // textfields
    private JTextField tfClassId, tfSemester;
    private JComboBox<String> cbCourses;

    public ClassesManagementDialog(CourseClassManagementGUI theManagementMainFrame, ClassesDAO theClDAO, Classes thePreviousClasses, boolean theUpdateMode) {
        this();
        managementMainFrame = theManagementMainFrame;
        clDAO = theClDAO;
        previousClasses = thePreviousClasses;
        updateMode = theUpdateMode;

        if (updateMode) {
            setTitle("Update Class");
            populateFields();
        }
    }
    
    public ClassesManagementDialog() {
        // ---- properties ----

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(100, 200);
        setSize(400, 300);
        setResizable(false);
        setTitle("Register Class");

        /* ---- Panels ---- */
        JPanel mainPanel = new JPanel();

        JPanel formPanel = new JPanel();
        JPanel btnPanel = new JPanel();

        /* -- main panel -- */
        mainPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        mainPanel.setLayout(new BorderLayout());

        /* -- form panel -- */
        formPanel.setLayout(new GridLayout(6, 1, 4, 2));
        formPanel.setPreferredSize(new Dimension(300, 200));

        // initializing labels
        JLabel lblClassId = new JLabel("Class ID: (max: 3)");
        JLabel lblSemester = new JLabel("Semester: (numbers only)");
        JLabel lblCourse = new JLabel("Course:");

        // initializing textfields
        tfClassId = new JTextField();
        tfSemester = new JTextField();

        // combo box
        // getting all current courses registered
        List<String> cbItemsArray = new ArrayList<String>();
        try {
            CoursesDAO coursesDAO = new CoursesDAO();
            Courses theCourse;
            List<Courses> courses = coursesDAO.getAllCourses();
            for(int i = 0; i < courses.size(); i++) {
                theCourse = courses.get(i);
                cbItemsArray.add(theCourse.getC_name());
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(ClassesManagementDialog.this, "Error",
                        "ERROR:\n" + exc, JOptionPane.ERROR_MESSAGE);
        }
        String[] cbItems = new String[cbItemsArray.size()];
        cbItemsArray.toArray(cbItems);
        cbCourses =  new JComboBox<>(cbItems);
        cbCourses.setSelectedIndex(-1);;
        cbCourses.setBackground(Color.WHITE);

        // -- button panel --

        // save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveRegister();
            }
        });

        // cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                ;
            }
        });

        /* ---- Adding elements to the window ---- */
        btnPanel.add(saveButton);
        btnPanel.add(cancelButton);

        formPanel.add(lblClassId);
        formPanel.add(tfClassId);
        formPanel.add(lblSemester);
        formPanel.add(tfSemester);
        formPanel.add(lblCourse);
        formPanel.add(cbCourses);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /* ------------------------ */

    private void saveRegister() {
        // -- get class info --

        String classId = tfClassId.getText();
        int semester = Integer.parseInt(tfSemester.getText());

        // getting course
        if (cbCourses.getSelectedIndex() < 0 ) { // checking if course is selected
            JOptionPane.showMessageDialog(ClassesManagementDialog.this, "You must select a course.", getTitle(), JOptionPane.WARNING_MESSAGE);
            return;
        }
        Courses course = new Courses();
        try {
            CoursesDAO coursesDAO = new CoursesDAO();
            String courseName = (String) cbCourses.getSelectedItem();

            course = coursesDAO.searchCoursesName(courseName);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(managementMainFrame, "ERROR: " + exc,
                        getTitle(), JOptionPane.ERROR_MESSAGE);
        }
        // initializing classes object
        Classes tempClasses = null;

        /* -- creating Classes object -- */

        // checking fields
        if (checkFields(classId, semester)) {
            // checking update mode
            if (updateMode) {
                // save updated info in tempclasses object
                tempClasses = previousClasses;
                tempClasses.setIdClass(classId);
                tempClasses.setSemester(semester);
                tempClasses.setCourses(course);
            } else {
                // save info in tempclasses object
                tempClasses = new Classes(classId, semester, course);
            }

            /* -- executing insert/update action -- */
            try {
                if (updateMode) {
                    clDAO.updateClass(tempClasses);
                    setVisible(false);
                    dispose();
                } else {
                    clDAO.addClass(tempClasses);
                    clearFields();
                }
                managementMainFrame.refreshTable();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(managementMainFrame, "ERROR:\n" + exc,
                        getTitle(), JOptionPane.ERROR_MESSAGE);
            }
        } else {return;}
    }

    private boolean checkFields(String classId, int semester) {
        if (classId.length() >=2 && semester > 0) {
            if (classId.length() <=3 && semester <= 20) {
                return true;
            } else {
                JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        "Check the character limit for the fields. Semester caps at 20", getTitle(), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        "Class ID be at least two characters long and semester cannot be 0 or lower", getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void populateFields() {
        tfClassId.setText(previousClasses.getIdClass());
        tfClassId.setEditable(false);
        tfSemester.setText(Integer.toString(previousClasses.getSemester()));

        // selecting previous course
        try {
            CoursesDAO coursesDAO = new CoursesDAO();
            int courseId = previousClasses.getCourses().getIdCourse();
            Courses courses = coursesDAO.searchCoursesId(courseId).get(0);
            cbCourses.setSelectedItem(courses.getC_name());
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        tfClassId.setText("");
        tfSemester.setText("");
        cbCourses.setSelectedIndex(-1);
    }
}
