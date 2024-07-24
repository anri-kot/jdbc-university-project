package universityGUI.professorsGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import universityCore.Courses;
import universityCore.Professors;
import universityDAO.CoursesDAO;
import universityDAO.ProfessorsDAO;
import universityGUI.StudentFacultyManagementGUI;

public class ProfessorsManagementDialog extends JDialog {
    StudentFacultyManagementGUI managementMainFrame;
    ProfessorsDAO prDAO;
    Professors previousProfessors;
    boolean updateMode;
    int currentUser;

    private JTextField tfName, tfAddress, tfPhone, tfSsn, tfSalary;
    private JComboBox<String> cbCourses;

    public ProfessorsManagementDialog(StudentFacultyManagementGUI theManagementMainFrame, ProfessorsDAO thePrDAO, Professors thePreviouProfessors, boolean theUpdateMode, int idUser) {
        this();
        managementMainFrame = theManagementMainFrame;
        prDAO = thePrDAO;
        previousProfessors = thePreviouProfessors;
        updateMode = theUpdateMode;
        currentUser = idUser;

        if (updateMode) {
            setTitle("Update Professor");
            populateTxtFields();
        }
    }

    public ProfessorsManagementDialog() {
        // ---- properties ----

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 440));
        setResizable(false);
        setTitle("Register Professor");

        // ---- window objects ----

        // -- form panel --

        // form conteiner
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(12, 1, 4, 2));
        formPanel.setPreferredSize(new Dimension(300, 330));

        // initializing labels
        JLabel lblName = new JLabel("Name (max 75):");
        JLabel lblAddress = new JLabel("Adress (max 100):");
        JLabel lblPhone = new JLabel("Phone (max 11):");
        JLabel lblSsn = new JLabel("Ssn (max 11):");
        JLabel lblSalary = new JLabel("Salary: (numbers only)");
        JLabel lblCourse = new JLabel("Course:");

        // initializing textfields
        tfName = new JTextField();
        tfAddress = new JTextField();
        tfPhone = new JTextField();
        tfSsn = new JTextField();
        tfSalary = new JTextField();

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
            JOptionPane.showMessageDialog(ProfessorsManagementDialog.this, "Error",
                        "ERROR: " + exc, JOptionPane.ERROR_MESSAGE);
        }
        String[] cbItems = new String[cbItemsArray.size()];
        cbItemsArray.toArray(cbItems);
        cbCourses =  new JComboBox<>(cbItems);
        cbCourses.setSelectedIndex(-1);
        cbCourses.setBackground(Color.WHITE);

        // -- buttons --

        // save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveProfessor();
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

        // buttons conteiner
        JPanel btnPanel = new JPanel();

        // objects container
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        mainPanel.setLayout(new BorderLayout());

        /* ---- Add to the dialog ---- */

        // adding fields and labels
        formPanel.add(lblName);
        formPanel.add(tfName);
        formPanel.add(lblAddress);
        formPanel.add(tfAddress);
        formPanel.add(lblPhone);
        formPanel.add(tfPhone);
        formPanel.add(lblSsn);
        formPanel.add(tfSsn);
        formPanel.add(lblSalary);
        formPanel.add(tfSalary);
        formPanel.add(lblCourse);
        formPanel.add(cbCourses);

        // adding buttons to the conteiner
        btnPanel.add(saveButton);
        btnPanel.add(cancelButton);

        // adding panels to the window
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    /* -- Button Event Handlers -- */

    // save
    public void saveProfessor() {
        // get professor info
        String name = tfName.getText();
        String address = tfAddress.getText();
        String phone = tfPhone.getText();
        String ssn = tfSsn.getText();
        double salary = Double.parseDouble(tfSalary.getText());

        // getting course

        if (cbCourses.getSelectedIndex() < 0 ) {
            int confirm = JOptionPane.showConfirmDialog(managementMainFrame, "Course not added.\nContinue?", getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
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

        Professors tempProfessors = null;

        if (checkFields(name, address, phone, ssn, salary)) {

            if (updateMode) {
                // save updated info in tempProfessors object
                tempProfessors = previousProfessors;
                tempProfessors.setName(name);
                tempProfessors.setAddress(address);
                tempProfessors.setPhone(phone);
                tempProfessors.setSsn(ssn);
                tempProfessors.setSalary(salary);
                tempProfessors.setCourse(course);

            } else {
                // save info in tempProfessors object
                tempProfessors = new Professors(0, name, address, phone, ssn, salary, course);
            }

            try {
                // send tempProfessors object info to be added
                if (updateMode) {
                    prDAO.updateProfessors(tempProfessors, currentUser);
                    setVisible(false);
                    dispose();
                } else {
                    prDAO.addProfessors(tempProfessors, currentUser);
                    clearFields();
                }

                // refresh list
                managementMainFrame.refreshTable();

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(managementMainFrame, "ERROR: " + exc,
                        getTitle(), JOptionPane.ERROR_MESSAGE);
            }

        } else {return;}
    }

    private boolean checkFields(String name, String address, String phone, String ssn, double salary) {
        if (Stream.of(name, address, phone, ssn).allMatch(s -> s.length() >= 2) || salary > 100) {
            if (name.length() <= 75 ||
                    address.length() <= 100 ||
                    phone.length() <= 11 ||
                    ssn.length() <= 11) {
                return true;
            } else {
                JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        "Character limit exceeded in one of the fields or salary is be lower than 100", getTitle(), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        "All fields must be at least two characters long and salary must be higher than 100", getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void populateTxtFields() {
        tfName.setText(previousProfessors.getName());
        tfAddress.setText(previousProfessors.getAddress());
        tfPhone.setText(previousProfessors.getPhone());
        tfSsn.setText(previousProfessors.getSsn());
        tfSalary.setText(Double.toString(previousProfessors.getSalary()));

        // selecting previous course
        try {
            CoursesDAO coursesDAO = new CoursesDAO();
            int courseId = previousProfessors.getCourse().getIdCourse();
            Courses courses = coursesDAO.searchCoursesId(courseId).get(0);
            cbCourses.setSelectedItem(courses.getC_name());
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        tfName.setText("");
        tfAddress.setText("");
        tfPhone.setText("");
        tfSsn.setText("");
        tfSalary.setText("");
        cbCourses.setSelectedIndex(-1);
    }
}
