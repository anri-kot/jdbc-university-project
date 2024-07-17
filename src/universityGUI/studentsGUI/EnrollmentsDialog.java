package universityGUI.studentsGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Stream;

import universityCore.Classes;
import universityCore.Enrollments;
import universityCore.Students;
import universityDAO.EnrollmentsDAO;

public class EnrollmentsDialog extends JDialog {

    private EnrollmentsDAO enrDAO;
    private StudentEnrollmentDialog stdEnrDiag;
    private Enrollments previousEnrollments;
    // text fields
    private JTextField tfYear, tfMatr, tfClass;
    private boolean updateMode;

    // Constructor that will be used for the addEnrollmentsDialog
    public EnrollmentsDialog(StudentEnrollmentDialog theStdEnrDiag, EnrollmentsDAO theEnrDAO, int st_matr, Enrollments thePreviousEnrollments,
            boolean theUpdateMode) {
        this(st_matr);
        stdEnrDiag = theStdEnrDiag;
        enrDAO = theEnrDAO;
        previousEnrollments = thePreviousEnrollments;
        updateMode = theUpdateMode;

        if (updateMode) {
            setTitle("Update Enrollment");
            populateTxtFields();
        }
    }

    public EnrollmentsDialog(int matr) {
        // ---- properties ----

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(400, 100);
        setMinimumSize(new Dimension(500, 350));
        setResizable(false);
        setTitle("Register enrollment");

        // ---- window objects ----

        // -- form panel --

        // initializing labels and txt fields
        JLabel lblYear = new JLabel("Enrollment year:");
        tfYear = new JTextField();
        JLabel lblMatr = new JLabel("Student ID:");
        tfMatr = new JTextField();
        tfMatr.setText(Integer.toString(matr));
        tfMatr.setEditable(false);
        JLabel lblClass = new JLabel("Class:");
        tfClass = new JTextField();

        // form conteiner
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 1, 4, 2));
        formPanel.setPreferredSize(new Dimension(300, 250));
        // adding fields and labels
        formPanel.add(lblYear);
        formPanel.add(tfYear);
        formPanel.add(lblMatr);
        formPanel.add(tfMatr);
        formPanel.add(lblClass);
        formPanel.add(tfClass);

        // -- buttons --

        // save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEnrollment();
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
        add(mainPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);

    }

    public void saveEnrollment() {
        // get enrollments info
        int year = Integer.parseInt(tfYear.getText());
        int matr = Integer.parseInt(tfMatr.getText());
        String idClass = tfClass.getText();

        // initializing objects
        Enrollments tempEnrollments = null;
        Classes genericClasses = new Classes();
        Students genericStudents = new Students();
        // setting student and class id
        genericClasses.setIdClass(idClass);
        genericStudents.setMatr(matr);

        if (checkFields(year, matr, idClass) == true) {

            if (updateMode) {
                // save updated info in tempEnrollments object
                tempEnrollments = previousEnrollments;
                tempEnrollments.setEn_year(year);
                tempEnrollments.setStudents(genericStudents);
                tempEnrollments.setClasses(genericClasses);;
            } else {
                // save info in tempEnrollments object
                tempEnrollments = new Enrollments(0, year, genericStudents, genericClasses);
            }

            try {
                // send tempEnrollments object info to be added
                if (updateMode) {
                    enrDAO.updateEnrollments(tempEnrollments);
                } else {
                    enrDAO.addEnrollments(tempEnrollments);
                }

                // close window
                setVisible(false);
                dispose();


                // refresh list
                stdEnrDiag.refreshEnrollmentsList();

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(stdEnrDiag, "Error " + exc,
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            // close window
            setVisible(false);
            dispose();
        }

    }

    public void populateTxtFields() {
        tfYear.setText(Integer.toString(previousEnrollments.getEn_year()));
        tfMatr.setText(Integer.toString(previousEnrollments.getStudents().getMatr()));
        tfClass.setText(previousEnrollments.getClasses().getIdClass());
    }

    public boolean checkFields(int theYear, int theMatr, String idClass) {
        String year = Integer.toString(theYear);
        String matr = Integer.toString(theMatr);
        if (Stream.of(year, matr, idClass).anyMatch(s -> s.length() >= 1)) {
            if (year.length() <= 4 ||
                    matr.length() <= 8 ||
                    idClass.length() <= 4) {
                return true;
            } else {
                JOptionPane.showMessageDialog(stdEnrDiag, "Error",
                        "Character limit exceeded in one of the fields", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(stdEnrDiag, "Error",
                    "Each field requires at least one character length", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
