package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

import model.User;
import model.Admin;
import model.Customer;
import manager.DatabaseManager;
import exception.InvalidLoginException;

// Login frame dengan fitur register
public class MainApp extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblStatus;

    private DatabaseManager dbManager;

    public MainApp() {
        dbManager = DatabaseManager.getInstance();

        setTitle("Sistem Pemesanan Tiket Travel - Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel lblTitle = new JLabel("SISTEM PEMESANAN TIKET TRAVEL", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        formPanel.add(txtPassword, gbc);

        // Button panel
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));

        btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);

        JButton btnRegister = new JButton("REGISTER");
        btnRegister.setBackground(new Color(34, 139, 34));
        btnRegister.setForeground(Color.WHITE);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);
        formPanel.add(buttonPanel, gbc);

        // Status label
        gbc.gridy = 3;
        lblStatus = new JLabel("", SwingConstants.CENTER);
        formPanel.add(lblStatus, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        addEventListeners(btnRegister);
    }

    private void addEventListeners(JButton btnRegister) {
        btnLogin.addActionListener(e -> handleLogin());
        txtPassword.addActionListener(e -> handleLogin());
        btnRegister.addActionListener(e -> new RegisterFrame());
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Username dan Password wajib diisi!");
            lblStatus.setForeground(Color.RED);
            return;
        }

        lblStatus.setText("Authenticating...");
        lblStatus.setForeground(Color.BLUE);
        btnLogin.setEnabled(false);

        // Background authentication menggunakan SwingWorker
        SwingWorker<User, Void> worker = new SwingWorker<>() {
            @Override
            protected User doInBackground() throws Exception {
                return dbManager.getUserDAO().authenticate(username, password);
            }

            @Override
            protected void done() {
                try {
                    User user = get();
                    openDashboard(user);
                    dispose();
                } catch (ExecutionException ex) {
                    Throwable cause = ex.getCause();
                    lblStatus.setText(cause instanceof InvalidLoginException
                            ? cause.getMessage()
                            : "Terjadi kesalahan sistem");
                    lblStatus.setForeground(Color.RED);
                } catch (InterruptedException ex) {
                    lblStatus.setText("Proses login terganggu");
                } finally {
                    btnLogin.setEnabled(true);
                    txtPassword.setText("");
                }
            }
        };
        worker.execute();
    }

    private void openDashboard(User user) {
        if (user instanceof Admin) {
            new DashboardFrame((Admin) user);
        } else if (user instanceof Customer) {
            new DashboardFrame((Customer) user);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}