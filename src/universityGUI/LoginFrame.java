package universityGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import universityDAO.UsersDAO;

public class LoginFrame extends JFrame implements Fonts {
    
    public LoginFrame() {
        /* ---- FRAME PROPERTIES ---- */
        setSize(320, 270);
        setLocationRelativeTo(null);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* ---- Panels ---- */
        JPanel mainPanel, northPanel, centerPanel, southPanel;
        mainPanel = new JPanel();
        northPanel = new JPanel();
        centerPanel = new JPanel();
        southPanel = new JPanel();

        /* -- main panel -- */
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        /* -- north panel -- */
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.setPreferredSize(new Dimension(320, 30));

        // Login Label Conteiner
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout());

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(SEGOE_BOLD_BIG);

        /* -- center panel -- */
        centerPanel.setLayout(new GridLayout(4, 1, 5, 5));

        JLabel lbUser = new JLabel("Username:");
        lbUser.setFont(SEGOE_PLAIN);
        JLabel lbPass = new JLabel("Password:");
        lbPass.setFont(SEGOE_PLAIN);
        JTextField tfUser = new JTextField();
        tfUser.setFont(SEGOE_PLAIN);
        JPasswordField tfPass = new JPasswordField();
        tfUser.setFont(SEGOE_PLAIN);

        /* -- south panel -- */
        southPanel.setLayout(new FlowLayout());

        // Button Conteiner
        JPanel bttnPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(156, 255, 156));
        // button action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser(tfUser, tfPass);
            }
        });
        
        /* ---- Adding components to the frame ---- */

        // -- north
        loginPanel.add(loginLabel);
        northPanel.add(loginPanel);

        // -- center
        centerPanel.add(lbUser);
        centerPanel.add(tfUser);
        centerPanel.add(lbPass);
        centerPanel.add(tfPass);

        // -- south
        bttnPanel.add(loginButton);
        southPanel.add(bttnPanel);

        // -- main
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        //  -- frame --
        add(mainPanel);
    }

    // check database for username
    public void loginUser(JTextField tfUsername, JPasswordField tfPassword) {
        String srcUsername = tfUsername.getText();
        @SuppressWarnings("deprecation")
        String srcPassword = tfPassword.getText();
        try {
            UsersDAO dao = new UsersDAO();
            // check if user exists
            int userId = dao.searchUserId(srcUsername);
            // error message if no user is found
            if (userId <= 0) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Username incorrect.", getTitle(), JOptionPane.ERROR_MESSAGE);
            } else {
                // call password check helper method
                boolean check = loginPass(srcPassword, userId);
                if (check) {
                    MainGUI mainGUI = new MainGUI(userId);
                    mainGUI.setVisible(true);
                    setVisible(false);
                    dispose();
                }
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(LoginFrame.this, "Error:\n" + exc, getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // check database for password
    public boolean loginPass(String tryPass, int userId) throws Exception {

        UsersDAO dao = new UsersDAO();
        String foundPass = dao.getUserpassword(userId);

        // error message
        if (!tryPass.equals(foundPass)) {
            JOptionPane.showMessageDialog(LoginFrame.this, "Password incorrect.", getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;
        } 
        return true;
    }

    public static void main(String[] args) {
        LoginFrame frame = new LoginFrame();
        frame.setVisible(true);
    }
}
