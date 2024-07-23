package universityGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainGUI extends JFrame {
    char entity;
    
    public MainGUI(int currentUser) {

        /* ---- frame properties */
        setTitle("Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(615, 330));
        setLocation(420, 140);
        setResizable(true);
        setBackground(Color.WHITE);

        /* ---- Panels ---- */
        JPanel mainPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();

        JPanel lblPanel = new JPanel();
        JPanel bttnPanel = new JPanel();

        /* ---- main panel ---- */
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.setMinimumSize(new Dimension(600, 500));

        /* -- west panel -- */
        westPanel.setPreferredSize(new Dimension(400, 330));
        westPanel.setBackground(Color.BLUE);

        /* -- east panel -- */
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(200, 330));

        /* - label panel */
        lblPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        lblPanel.setPreferredSize(new Dimension(200, 50));
        // top label
        JLabel topLabel = new JLabel("Choose what to manage");

        /* - buttons panel - */
        bttnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bttnPanel.setPreferredSize(new Dimension(50, 450));
        
        /* buttons */
        JButton studentsB = new JButton("Students");
        JButton professorsB = new JButton("Professors");
        JButton classesB = new JButton("Classes");
        JButton coursesB = new JButton("Courses");

        // students button
        studentsB.setPreferredSize(new Dimension(150, 25));
        studentsB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    entity = 's';
                    StudentFacultyManagementGUI std = new StudentFacultyManagementGUI(currentUser, entity);
                    std.setVisible(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // professors button
        professorsB.setPreferredSize(new Dimension(150, 25));
        professorsB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    entity = 'p';
                    StudentFacultyManagementGUI std = new StudentFacultyManagementGUI(currentUser, entity);
                    std.setVisible(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // classes button
        classesB.setPreferredSize(new Dimension(150, 25));

        // courses button
        coursesB.setPreferredSize(new Dimension(150, 25));

        /* ---- adding components to panels ---- */
        lblPanel.add(topLabel, gbc);

        bttnPanel.add(studentsB);
        bttnPanel.add(professorsB);
        bttnPanel.add(classesB);
        bttnPanel.add(coursesB);

        //eastPanel.add(topLabel, BorderLayout.NORTH);
        eastPanel.add(lblPanel, BorderLayout.NORTH);
        eastPanel.add(bttnPanel, BorderLayout.CENTER);

        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(eastPanel, BorderLayout.EAST);

        add(mainPanel);
        pack();
    }
}
