package universityGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import universityCore.Students;
import universityDAO.StudentsDAO;

public class StudentsSearchApp extends JFrame {
    private StudentsDAO dao;

    // launch app
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StudentsSearchApp stdSearch = new StudentsSearchApp();
                    stdSearch.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public StudentsSearchApp() {
        // create DAO
        try {
            dao = new StudentsDAO();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // ---- frame properties ----
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setMinimumSize(new Dimension(500, 476));
        setTitle("Search Students");

        // ---- frame objects ----
        // top panel
        JPanel topSrcPanel = new JPanel();
        topSrcPanel.setPreferredSize(new Dimension(470, 40));
        topSrcPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        // top panel label
        JLabel label = new JLabel();
        label.setText("Search by name: ");
        label.setHorizontalAlignment(JLabel.LEFT);
        topSrcPanel.add(label);
        // top panel text field
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(250, 25));
        topSrcPanel.add(searchTextField);
        // top panel search button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // search action
                try {
                    String name = searchTextField.getText();
                    List<Students> students = null;

                    if (name != null && name.trim().length() > 0) {
                        students = dao.searchStudents(name);
                    } else {
                        students = dao.getAllStudents();
                    }
                    for (Students temp : students) {
                        System.out.println(temp);
                    }
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(StudentsSearchApp.this, "Error: " + exc);
                }
            }
        });
        topSrcPanel.add(searchButton);

        // ---- add frame objects ----
        add(topSrcPanel);

    }

}
