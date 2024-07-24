package universityGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import universityDAO.UsersDAO;

public class MainGUI extends JFrame implements FontsAndColors {
    private char entity;
    private String name;
    
    public MainGUI(int currentUser) {

        try {
            UsersDAO userDAO = new UsersDAO();
            name = userDAO.getU_name(currentUser);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(MainGUI.this, "Couldn't get user name.\n" + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        /* ---- frame properties */
        setTitle("Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(615, 330));
        setLocation(420, 140);
        setResizable(false);
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
        //westPanel.setBackground(Color.BLUE);
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));

        JLabel imgLabel = new JLabel(new ImageIcon("img/ask2.jpg"));

        /* -- east panel -- */
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(200, 330));

        /* - label panel */
        lblPanel.setLayout(new GridBagLayout());
        lblPanel.setPreferredSize(new Dimension(200, 50));
        lblPanel.setBackground(new Color(255,255,255));
        
        GridBagConstraints top1 = new GridBagConstraints();
        GridBagConstraints top2 = new GridBagConstraints();
        top2.gridy = 1;
        
        // top labels
        JLabel topLabel1 = new JLabel("Welcome " + name);
        JLabel topLabel2 = new JLabel("Choose what to manage");
        
        topLabel1.setFont(SEGOE_BOLD);
        topLabel2.setFont(SEGOE_PLAIN);

        /* - buttons panel - */
        bttnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bttnPanel.setPreferredSize(new Dimension(50, 450));
        bttnPanel.setBackground(new Color(255,255,255));
        
        /* buttons */
        JButton studentsB = new JButton("Students");
        JButton professorsB = new JButton("Professors");
        JButton classesB = new JButton("Classes");
        JButton coursesB = new JButton("Courses");

        // students button
        studentsB.setPreferredSize(new Dimension(150, 25));
        studentsB.setFont(SEGOE_BOLD);
        studentsB.setBackground(PRIMARY_C);
        studentsB.setForeground(Color.WHITE);
        studentsB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entity = 's';
                StudentFacultyManagementGUI std = new StudentFacultyManagementGUI(currentUser, entity);
                std.setVisible(true);
            }
        });

        // professors button
        professorsB.setPreferredSize(new Dimension(150, 25));
        professorsB.setFont(SEGOE_BOLD);
        professorsB.setBackground(PRIMARY_C);
        professorsB.setForeground(Color.WHITE);
        professorsB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entity = 'p';
                StudentFacultyManagementGUI std = new StudentFacultyManagementGUI(currentUser, entity);
                std.setVisible(true);
            }
        });

        // classes button
        classesB.setPreferredSize(new Dimension(150, 25));
        classesB.setFont(SEGOE_BOLD);
        classesB.setBackground(SECONDARY_C);
        classesB.setForeground(Color.WHITE);
        // action
        classesB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entity = 'l'; // entity l, taken from the second letter of 'classes'
                CourseClassManagementGUI courseClassGUI = new CourseClassManagementGUI(currentUser, entity);
                courseClassGUI.setVisible(true);
            }
        });

        // courses button
        coursesB.setPreferredSize(new Dimension(150, 25));
        coursesB.setFont(SEGOE_BOLD);
        coursesB.setBackground(SECONDARY_C);
        coursesB.setForeground(Color.WHITE);

        /* ---- adding components to panels ---- */
        lblPanel.add(topLabel1, top1);
        lblPanel.add(topLabel2, top2);

        bttnPanel.add(studentsB);
        bttnPanel.add(professorsB);
        bttnPanel.add(classesB);
        bttnPanel.add(coursesB);

        westPanel.add(imgLabel);

        //eastPanel.add(topLabel, BorderLayout.NORTH);
        eastPanel.add(lblPanel, BorderLayout.NORTH);
        eastPanel.add(bttnPanel, BorderLayout.CENTER);

        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(eastPanel, BorderLayout.EAST);

        add(mainPanel);
        pack();
    }
}
