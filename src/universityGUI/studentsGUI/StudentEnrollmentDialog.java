package universityGUI.studentsGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.List;
import java.awt.*;
import universityCore.Students;
import universityCore.Enrollments;
import universityDAO.EnrollmentsDAO;


public class StudentEnrollmentDialog extends JDialog {
    // global variables
    private int matr;
    private Students students;
    private EnrollmentsDAO erlDAO;
    private List<Enrollments> enrollments;

    // constructor

    public StudentEnrollmentDialog(int st_matr) {
        matr = st_matr;
        /* Create DAO */
        try {
            erlDAO = new EnrollmentsDAO();
        } catch (Exception exc) {
            System.out.println(exc);
        }

        /* ---- Dialog Properties ---- */

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocation(600, 200);
        setMinimumSize(new Dimension(500, 400));
        //setResizable(false);
        setTitle("Enrollments");

        /* ---- PANELS---- */

        JPanel mainPanel = new JPanel();
        JScrollPane centerPanel;
        JPanel eastPanel = new JPanel();
        JPanel bttPanel = new JPanel();

        // -- main panel --
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // -- center panel --
        
        // table
        JTable table = new JTable();
        // fill table
        try {
            enrollments = erlDAO.searchEnrollmentsStId(matr);
            EnrollmentsTableModel model = new EnrollmentsTableModel(enrollments);
            System.out.println(enrollments);
            table.setModel(model);
        } 
        catch (Exception exc) {
            JOptionPane.showMessageDialog(StudentEnrollmentDialog.this, "Error:\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // -- east panel --

        // - buttons panel -
        bttPanel.setLayout(new GridLayout(3, 1, 0, 10));

        // - buttons -
        JButton addButton = new JButton("Add");
        JButton updButton = new JButton("Update");
        JButton delButton = new JButton("Delete");

        /* ---- Add to panels ---- */

        // buttons panel
        bttPanel.add(addButton);
        bttPanel.add(updButton);
        bttPanel.add(delButton);

        // center
        centerPanel = new JScrollPane(table); //no idea why i cant use add() for this

        // east
        eastPanel.add(bttPanel);

        // main
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(eastPanel, BorderLayout.EAST);

        // dialog
        add(mainPanel);
    }
}
