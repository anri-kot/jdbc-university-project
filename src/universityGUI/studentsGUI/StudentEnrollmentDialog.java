package universityGUI.studentsGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import universityCore.Enrollments;
import universityDAO.EnrollmentsDAO;
import universityGUI.FontsAndColors;


public class StudentEnrollmentDialog extends JDialog implements FontsAndColors {
    // global variables
    private int matr;
    private EnrollmentsDAO erlDAO;
    private List<Enrollments> enrollments;
    // fonts
    private Font segoePlain = new Font("Segoe UI", Font.PLAIN, 12);
    private Font segoeBold = new Font("Segoe UI", Font.BOLD, 12);
    // table
    JTable table;

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
        setLayout(new BorderLayout());;
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
        mainPanel.setBorder(new EmptyBorder(0, 5, 5, 5));

        // -- center panel --
        
        // table
        table = new JTable();
        table.setRowHeight(25);
        table.setFont(segoePlain);
        table.setShowHorizontalLines(false);
        // table header
        table.getTableHeader().setBackground(PRIMARY_C);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(segoeBold);
        // fill table
        try {
            enrollments = erlDAO.searchEnrollmentsStId(matr);
            EnrollmentsTableModel model = new EnrollmentsTableModel(enrollments);
            table.setModel(model);
        } 
        catch (Exception exc) {
            JOptionPane.showMessageDialog(StudentEnrollmentDialog.this, "Error:\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // -- east panel --
        eastPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        eastPanel.setPreferredSize(new Dimension(80, 1000));

        // image
        JLabel toru = new JLabel(new ImageIcon("img/toru.gif"));


        // - buttons panel -
        bttPanel.setLayout(new GridLayout(3, 1, 0, 10));

        // - buttons -
        JButton addButton = new JButton("Add");
        JButton updButton = new JButton("Update");
        JButton delButton = new JButton("Delete");

        // add button
        addButton.addActionListener(new ActionListener() {
            // pop dialog
            public void actionPerformed(ActionEvent e) {
                try {
                    EnrollmentsDialog enrDialog = new EnrollmentsDialog(StudentEnrollmentDialog.this, erlDAO, st_matr, null, false);
                    enrDialog.setVisible(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(StudentEnrollmentDialog.this, "Error:\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // update button
        updButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    // check if a row is selected
                    if (row < 0) { 
                        JOptionPane.showMessageDialog(StudentEnrollmentDialog.this, "You need to select an enrollment to update.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // get the selected row into an Enrollments object
                    Enrollments enrollments = (Enrollments) table.getValueAt(row, EnrollmentsTableModel.OBJECT_COL);

                    EnrollmentsDialog enrDialog = new EnrollmentsDialog(StudentEnrollmentDialog.this, erlDAO, st_matr, enrollments, true);
                    enrDialog.setVisible(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(StudentEnrollmentDialog.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // delete button
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow(); // get selected row
                    // check if row is selected
                    if (row < 0) {
                        JOptionPane.showMessageDialog(StudentEnrollmentDialog.this, "You need to select an enrollment to delete.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // get the selected row into an Enrollments object
                    Enrollments enrollments = (Enrollments) table.getValueAt(row, EnrollmentsTableModel.OBJECT_COL);

                    // pop confirm window
                    int confirm = JOptionPane.showConfirmDialog(StudentEnrollmentDialog.this, "Are you sure you want to delete this enrollment?", getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirm != JOptionPane.YES_OPTION) {
                        return;
                    }

                    // delete operation
                    erlDAO.deleteEnrollments(enrollments.getIdEnrollment());
                    //refresh
                    refreshEnrollmentsList();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(StudentEnrollmentDialog.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /* ---- Add to panels ---- */

        // buttons panel
        bttPanel.add(addButton);
        bttPanel.add(updButton);
        bttPanel.add(delButton);

        // center
        centerPanel = new JScrollPane(table); //no idea why i cant use add() for this

        // east
        eastPanel.add(bttPanel);
        eastPanel.add(toru);

        // main
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(eastPanel, BorderLayout.EAST);

        // dialog
        add(mainPanel, BorderLayout.CENTER);
    }

    public void refreshEnrollmentsList() {
        try {
            List<Enrollments> enrollments = erlDAO.searchEnrollmentsStId(matr);

            // create model and update list
            EnrollmentsTableModel model = new EnrollmentsTableModel(enrollments);
            table.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
