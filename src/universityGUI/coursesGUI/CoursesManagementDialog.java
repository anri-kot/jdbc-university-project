package universityGUI.coursesGUI;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import universityCore.Courses;
import universityDAO.CoursesDAO;
import universityGUI.CourseClassManagementGUI;

public class CoursesManagementDialog extends JDialog {

    private CourseClassManagementGUI managementMainFrame;
    private CoursesDAO coDAO;
    private Courses previousCourses;
    private boolean updateMode;

    // textfields
    private JTextField tfCourseName, tfCourseDuration;

    public CoursesManagementDialog(CourseClassManagementGUI managementMainFrame, CoursesDAO coDAO, Courses previousCourses, boolean updateMode) {
        this();
        this.managementMainFrame = managementMainFrame;
        this.coDAO = coDAO;
        this.previousCourses = previousCourses;
        this.updateMode = updateMode;

        if (updateMode) {
            setTitle("Update Course");
            populateFields();
        }
    }

    public CoursesManagementDialog() {
        /* ---- properties ---- */
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(100, 200);
        setSize(400, 220);
        setResizable(false);
        setTitle("Register Course");

        /* ---- Panels ---- */
        JPanel mainPanel = new JPanel();

        JPanel formPanel = new JPanel();
        JPanel btnPanel = new JPanel();

        /* -- main panel -- */
        mainPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        mainPanel.setLayout(new BorderLayout());

        /* -- form panel -- */
        formPanel.setLayout(new GridLayout(4, 1, 4, 2));
        formPanel.setPreferredSize(new Dimension(300, 125));

        // initializing labels
        JLabel lblCourseName = new JLabel("Course name: (max: 50)");
        JLabel lblCourseDuration = new JLabel("Course duration (h): (numbers only)");

        // initializing textfields
        tfCourseName = new JTextField();
        tfCourseDuration = new JTextField();

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

        formPanel.add(lblCourseName);
        formPanel.add(tfCourseName);
        formPanel.add(lblCourseDuration);
        formPanel.add(tfCourseDuration);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /* --------------------------- */

    private void populateFields() {
        tfCourseName.setText(previousCourses.getC_name());
        tfCourseDuration.setText(Integer.toString(previousCourses.getC_duration()));
    }

    private void saveRegister() {
        // -- get class info --
        String courseName = tfCourseName.getText();
        int courseDuration = Integer.parseInt(tfCourseDuration.getText());

        // initializing Courses object
        Courses tempCourses = null;

        /* -- creating Courses object -- */
        if (checkFields(courseName, courseDuration)) {
            // checking update mode
            if (updateMode) {
                tempCourses = previousCourses;
                // save update info in the tempCourses Object
                tempCourses.setC_name(courseName);
                tempCourses.setC_duration(courseDuration);
            } else {
                // create new Courses object
                tempCourses = new Courses(0, courseName, courseDuration);
            }

            /* -- executing insert/update action -- */
            try {
                if (updateMode) {
                    coDAO.updateCourse(tempCourses);
                    setVisible(false);
                    dispose();
                } else {
                    coDAO.addCourse(tempCourses);
                    clearFields();
                }
                managementMainFrame.refreshTable();

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(managementMainFrame, "ERROR:\n" + exc,
                        getTitle(), JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private boolean checkFields(String courseName, int courseDuration) {
        if (courseName.length() >= 2 && courseDuration > 0) {
            if (courseName.length() <= 50 && courseDuration < 7999) {
                return true;
            } else {
                JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        "Check the character limit for the Course Name. Course duration caps at 7999 hours", getTitle(), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        "Course Name must be at least two characters long and duration cannot be 0 or lower", getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void clearFields() {
        tfCourseName.setText("");
        tfCourseDuration.setText("");
    }
}
