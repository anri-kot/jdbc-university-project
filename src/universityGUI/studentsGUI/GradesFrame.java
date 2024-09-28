package universityGUI.studentsGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.List;

import universityCore.Grades;
import universityCore.Students;
import universityDAO.EnrollmentsDAO;
import universityDAO.GradesDAO;
import universityDAO.StudentsDAO;

public class GradesFrame extends JFrame {
    private StudentsDAO stDAO;
    private EnrollmentsDAO enDAO;
    private GradesDAO grDAO;
    
    // text fields
    private JTextField tfSearch;
    private JComboBox<String> cbClass;
    // table
    private JTable table;

    public GradesFrame(StudentsDAO stDAO, EnrollmentsDAO enDAO) {
        this();
        this.stDAO = stDAO;
        this.enDAO = enDAO;
    }
    
    public GradesFrame() {
        try {
            grDAO = new GradesDAO();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(GradesFrame.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }

        /* ---- properties ---- */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Students' Grades");
        setSize(750, 550);
        setLocationRelativeTo(null);

        /* ---- panels ---- */
        JPanel mainPanel = new JPanel();

        JPanel northMainPanel = new JPanel();
        JScrollPane centerMainPanel;
        JPanel southMainPanel = new JPanel();

        JPanel buttonPanel = new JPanel();

        /* -- main panel -- */
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        /* -- north panel -- */
        northMainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        // labels
        JLabel lblSearch = new JLabel("Search: ");
        JLabel lblClass = new JLabel("Class: ");
        
        tfSearch = new JTextField();
        tfSearch.setPreferredSize(new Dimension(250, 25));
        
        cbClass = new JComboBox<>();
        cbClass.setSelectedIndex(-1);
        
        // button
        JButton buttonSearch = new JButton("Search");
        
        buttonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String theName = tfSearch.getText();
                searchRegister(theName, cbClass);
            }
        });

        /* -- center panel -- */
        table = new JTable();
        centerMainPanel = new JScrollPane(table);


        /* -- south panel -- */
        
        // buttons
        JButton addGrade = new JButton("Add");
        JButton updateGrade = new JButton("Update");
        JButton deleteGrade = new JButton("Delete");

        // add
        addGrade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        /* ---- add to the frame ---- */
        buttonPanel.add(addGrade);
        buttonPanel.add(updateGrade);
        buttonPanel.add(deleteGrade);

        northMainPanel.add(lblSearch);
        northMainPanel.add(tfSearch);
        northMainPanel.add(lblClass);
        northMainPanel.add(cbClass);

        southMainPanel.add(buttonPanel);

        mainPanel.add(northMainPanel, BorderLayout.NORTH);
        mainPanel.add(centerMainPanel, BorderLayout.CENTER);
        mainPanel.add(southMainPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void searchRegister(String name, JComboBox<String> cbClass) {
        try {
            List<Students> students = null;
            
            if (cbClass.getSelectedIndex() >= 0) {
                if (name != null && name.trim().length() > 0) {
                    // filtering students
                    students = stDAO.searchStudentsByNameAndClass(name, (String) cbClass.getSelectedItem());
                } else {
                    // filtering students
                    students = stDAO.searchStudentsByClass((String) cbClass.getSelectedItem());
                }

            } else {
                JOptionPane.showMessageDialog(GradesFrame.this, "Select a class", getTitle(), JOptionPane.WARNING_MESSAGE);
                return;
            }

            // create model and update table
            //GradesTableModel model = new GradesTableModel();
        } catch (Exception exc) {

        }
    }

    public static void main(String[] args) {
        GradesFrame frame = new GradesFrame();
        frame.setVisible(true);
    }
}
