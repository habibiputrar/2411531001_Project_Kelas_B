package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.User;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public JTextField txtUsername;
    public JTextField txtPassword;
    public JButton btnLogin;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginFrame frame = new LoginFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginFrame() {
        setTitle("Laundry Apps");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 400);              
        setLocationRelativeTo(null);  
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(30, 40, 30, 40));
        contentPane.setBackground(new Color(230, 230, 230)); 
        setContentPane(contentPane);

        // Judul
        JLabel lblTitle = new JLabel("Laundry Apps");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.BLACK);

        JLabel lblSubtitle = new JLabel("Males aja nyuci, biar kami cuciin");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(100, 100, 100));

        // Username
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblUsername.setForeground(Color.BLACK);
        
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        txtUsername.setBackground(Color.WHITE);
        txtUsername.setColumns(10);

        // Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblPassword.setForeground(Color.BLACK);
        
        txtPassword = new JTextField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setColumns(10);

        // Tombol Login
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(User.login(txtUsername.getText(), txtPassword.getText())) {
                    new MainFrame().setVisible(true);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "Login Gagal");
                }
            }
        });
        btnLogin.setBackground(new Color(190, 190, 190)); 
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnLogin.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        btnLogin.setFocusPainted(false);

        GroupLayout gl = new GroupLayout(contentPane);
        gl.setHorizontalGroup(
        	gl.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl.createSequentialGroup()
        			.addGroup(gl.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblTitle)
        				.addComponent(lblSubtitle)
        				.addComponent(lblUsername)
        				.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblPassword)
        				.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
        				.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE))
        			.addGap(20))
        );
        gl.setVerticalGroup(
        	gl.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblTitle)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lblSubtitle)
        			.addGap(18)
        			.addComponent(lblUsername)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(lblPassword)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        			.addGap(37)
        			.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
        			.addGap(27))
        );
        contentPane.setLayout(gl);

        gl.setAutoCreateGaps(false);
        gl.setAutoCreateContainerGaps(false);
    }
}