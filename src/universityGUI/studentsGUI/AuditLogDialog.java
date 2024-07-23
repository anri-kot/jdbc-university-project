package universityGUI.studentsGUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import universityCore.logs.StudentsLog;
import universityDAO.StudentsLogDAO;
import universityDAO.UsersDAO;
import universityGUI.StudentFacultyManagementGUI;

public class AuditLogDialog extends JDialog {
    StudentFacultyManagementGUI stMain;

    public AuditLogDialog(StudentFacultyManagementGUI stMain, int theUserId) {
        this(theUserId);
        this.stMain = stMain;
    }
    
    public AuditLogDialog(int userId) {
        /* ---- dialog properties ---- */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setMinimumSize(new Dimension(600, 400));
        setTitle("Audit Log");
        setLocation(400, 100);

        /* ---- Panels ---- */
        JPanel mainPanel = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();

        /* -- main panel -- */
        mainPanel.setPreferredSize(new Dimension(600, 400));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.setBackground(Color.BLACK);

        /* -- north panel -- */
        northPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        // labels
        JLabel userLabel = new JLabel();
        try {
            UsersDAO userDAO = new UsersDAO();
            String userName = userDAO.getU_name(userId);
            userLabel.setText("Logged as: " + userName);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(AuditLogDialog.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }

        /* -- center panel -- */
        // table
        JTable table = new JTable();
        try {
            // get logs
            StudentsLogDAO dao = new StudentsLogDAO(); 
            List<StudentsLog> logs = dao.getLogs();

            LogTableModel model = new LogTableModel(logs);
            table.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(AuditLogDialog.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }
        
        JScrollPane centerPanel = new JScrollPane(table);

        /* ---- add to the dialog ---- */
        northPanel.add(userLabel);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
