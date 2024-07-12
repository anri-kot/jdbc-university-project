package universityGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import universityCore.Students;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import universityDAO.StudentsDAO;

public class AddStudentDialog extends JDialog {

    private StudentsDAO stdDAO;
    private StudentsSearchApp stdSearchApp;
    // text fields
    private JTextField tfName, tfAddress, tfPhone, tfSsn;

    // Constructor that will be used for the AddStudentDialog
    public AddStudentDialog(StudentsSearchApp theStdSearchApp, StudentsDAO theStdDAO) {
        this();
        stdSearchApp = theStdSearchApp;
        stdDAO = theStdDAO;
    }

    public AddStudentDialog() {
        // ---- properties ----

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 350));
        setResizable(false);
        setTitle("Register student");

        // ---- window objects ----

        // -- form panel --

        // initializing labels and txt fields
        JLabel lblName = new JLabel("Name:");
        tfName = new JTextField();
        JLabel lblAddress = new JLabel("Adress:");
        tfAddress = new JTextField();
        JLabel lblPhone = new JLabel("Phone:");
        tfPhone = new JTextField();
        JLabel lblSsn = new JLabel("Ssn:");
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
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
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
        add(mainPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);

    }

    public void saveStudent() {
        // get student info
        String name = tfName.getText();
        String address = tfAddress.getText();
        String phone = tfPhone.getText();
        String ssn = tfSsn.getText();

        // save info in tempStudents object
        Students tempStudents = new Students(0, name, address, phone, ssn);

        try {
            // send tempStudents object info to be added
            stdDAO.AddStudent(tempStudents);

            // close window
            setVisible(false);
            dispose();

            // refresh list
            stdSearchApp.refreshStudentList();

            JOptionPane.showMessageDialog(stdSearchApp, "Student registered sucessfully.",
                    "Student registered", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {

        }
    }

}
