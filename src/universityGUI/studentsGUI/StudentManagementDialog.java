package universityGUI.studentsGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Stream;
import universityCore.Students;
import universityDAO.StudentsDAO;
import universityGUI.StudentFacultyManagementGUI;

public class StudentManagementDialog extends JDialog {

    private StudentsDAO stdDAO;
    private StudentFacultyManagementGUI managementMainFrame;
    private Students previousStudents;
    private int currentUser;
    // text fields
    private JTextField tfName, tfAddress, tfPhone, tfSsn;
    private boolean updateMode;

    // Constructor that will be used for the addStudentDialog
    public StudentManagementDialog(StudentFacultyManagementGUI theMainFrame, StudentsDAO theStdDAO, Students thePreviousStudents,
            boolean theUpdateMode, int userId) {
        this();
        managementMainFrame = theMainFrame;
        updateMode = theUpdateMode;
        stdDAO = theStdDAO;
        previousStudents = thePreviousStudents;
        currentUser = userId;

        if (updateMode) {
            setTitle("Update Student");
            populateTxtFields();
        }
    }

    public StudentManagementDialog() {
        // ---- properties ----

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(400, 100);
        setMinimumSize(new Dimension(500, 350));
        setResizable(false);
        setTitle("Register Student");

        // ---- window objects ----

        // -- form panel --

        // initializing labels and txt fields
        JLabel lblName = new JLabel("Name (max 75):");
        tfName = new JTextField();
        JLabel lblAddress = new JLabel("Adress (max 100):");
        tfAddress = new JTextField();
        JLabel lblPhone = new JLabel("Phone (max 11):");
        tfPhone = new JTextField();
        JLabel lblSsn = new JLabel("Ssn (max 11):");
        tfSsn = new JTextField();

        // form conteiner
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 1, 4, 2));
        formPanel.setPreferredSize(new Dimension(300, 250));
        // adding fields and labels
        formPanel.add(lblName);
        formPanel.add(tfName);
        formPanel.add(lblAddress);
        formPanel.add(tfAddress);
        formPanel.add(lblPhone);
        formPanel.add(tfPhone);
        formPanel.add(lblSsn);
        formPanel.add(tfSsn);

        // -- buttons --

        // save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveStudent();
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
        btnPanel.add(saveButton);
        btnPanel.add(cancelButton);

        // objects container
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        mainPanel.setLayout(new BorderLayout());

        // ---- add objects to the window
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);

    }

    public void saveStudent() {
        // get student info
        String name = tfName.getText();
        String address = tfAddress.getText();
        String phone = tfPhone.getText();
        String ssn = tfSsn.getText();

        Students tempStudents = null;

        if (checkFields(name, address, phone, ssn) == true) {

            if (updateMode) {
                // save updated info in tempStudents object
                tempStudents = previousStudents;
                tempStudents.setName(name);
                tempStudents.setAddress(address);
                tempStudents.setPhone(phone);
                tempStudents.setSsn(ssn);

            } else {
                // save info in tempStudents object
                tempStudents = new Students(0, name, address, phone, ssn);
            }

            try {
                // send tempStudents object info to be added
                if (updateMode) {
                    stdDAO.updateStudent(tempStudents, currentUser);
                    setVisible(false);
                    dispose();
                } else {
                    stdDAO.addStudent(tempStudents, currentUser);
                    clearFields();
                }

                // refresh list
                managementMainFrame.refreshTable();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(managementMainFrame, "Error",
                        "ERROR: " + e, JOptionPane.ERROR_MESSAGE);
            }

        } else {
            // close window
            setVisible(false);
            dispose();
        }
    }

    public void populateTxtFields() {
        tfName.setText(previousStudents.getName());
        tfAddress.setText(previousStudents.getAddress());
        tfPhone.setText(previousStudents.getPhone());
        tfSsn.setText(previousStudents.getSsn());
    }

    public void clearFields() {
        tfName.setText("");
        tfAddress.setText("");
        tfPhone.setText("");
        tfSsn.setText("");
    }

    public boolean checkFields(String name, String address, String phone, String ssn) {
        if (Stream.of(name, address, phone, ssn).allMatch(s -> s.length() >= 2)) {
            if (name.length() <= 75 ||
                    address.length() <= 100 ||
                    phone.length() <= 11 ||
                    ssn.length() <= 11) {
                return true;
            } else {
                JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                        "Character limit exceeded in one of the fields", getTitle(), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(managementMainFrame, "Error:\n" +
                    "Each field requires at least two characters length", getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
