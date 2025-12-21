package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Customer;
import manager.DatabaseManager;
import exception.ValidationException;

/**
 * RegisterFrame
 * GUI untuk registrasi customer baru
 * 
 * @author Habibi Putra Rizqullah (2411531001)
 */
public class RegisterFrame extends JFrame {
    
    // Components
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtFullName;
    private JButton btnRegister;
    private JButton btnCancel;
    private JLabel lblStatus;
    
    // Database Manager
    private DatabaseManager dbManager;
    
    /**
     * Constructor
     */
    public RegisterFrame() {
        dbManager = DatabaseManager.getInstance();
        
        // Setup Frame
        setTitle("Registrasi Customer Baru");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize Components
        initComponents();
        
        // Set visible
        setVisible(true);
    }
    
    /**
     * Initialize GUI Components
     */
    private void initComponents() {
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel lblTitle = new JLabel("REGISTRASI CUSTOMER BARU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitle);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblFullName = new JLabel("Nama Lengkap:");
        lblFullName.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblFullName, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtFullName = new JTextField(20);
        txtFullName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtFullName, gbc);
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblUsername, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtUsername, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtPassword, gbc);
        
        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel lblConfirmPassword = new JLabel("Konfirmasi Password:");
        lblConfirmPassword.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblConfirmPassword, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        txtConfirmPassword = new JPasswordField(20);
        txtConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtConfirmPassword, gbc);
        
        // Button Panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        btnRegister = new JButton("REGISTER");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setBackground(new Color(70, 130, 180));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setPreferredSize(new Dimension(120, 35));
        
        btnCancel = new JButton("BATAL");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setBackground(new Color(220, 20, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnCancel);
        formPanel.add(buttonPanel, gbc);
        
        // Status Label
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        lblStatus = new JLabel("");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 12));
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblStatus, gbc);
        
        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(240, 248, 255));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblInfo = new JLabel("<html><center>" +
                                    "Username minimal 4 karakter<br>" +
                                    "Password minimal 6 karakter</center></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(lblInfo);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Add Event Listeners
        addEventListeners();
    }
    
    /**
     * Add Event Listeners
     */
    private void addEventListeners() {
        // Register button action
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        
        // Cancel button action
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Enter key on confirm password field
        txtConfirmPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleRegister();
                }
            }
        });
    }
    
    /**
     * Handle Registration Process
     * EXCEPTION HANDLING: Validasi input lengkap
     */
    private void handleRegister() {
        String fullName = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        try {
            // Validasi Full Name
            if (fullName.isEmpty()) {
                throw new ValidationException("Nama lengkap tidak boleh kosong!");
            }
            
            if (fullName.length() < 3) {
                throw new ValidationException("Nama lengkap minimal 3 karakter!");
            }
            
            // Validasi Username
            if (username.isEmpty()) {
                throw new ValidationException("Username tidak boleh kosong!");
            }
            
            if (username.length() < 4) {
                throw new ValidationException("Username minimal 4 karakter!");
            }
            
            // Cek username sudah ada atau belum
            if (dbManager.getUserDAO().findByUsername(username) != null) {
                throw new ValidationException("Username sudah digunakan! Pilih username lain.");
            }
            
            // Validasi Password
            if (password.isEmpty()) {
                throw new ValidationException("Password tidak boleh kosong!");
            }
            
            if (password.length() < 6) {
                throw new ValidationException("Password minimal 6 karakter!");
            }
            
            // Validasi Confirm Password
            if (!password.equals(confirmPassword)) {
                throw new ValidationException("Password dan konfirmasi password tidak sama!");
            }
            
            // Generate User ID
            String userId = generateUserId();
            
            // Buat Customer object
            Customer newCustomer = new Customer(userId, username, password, fullName);
            
            // Simpan ke database
            boolean success = dbManager.getUserDAO().save(newCustomer);
            
            if (success) {
                lblStatus.setText("Registrasi berhasil!");
                lblStatus.setForeground(new Color(0, 128, 0));
                
                // Show success message
                JOptionPane.showMessageDialog(this, 
                    "Registrasi berhasil!\n\n" +
                    "User ID: " + userId + "\n" +
                    "Username: " + username + "\n" +
                    "Nama: " + fullName + "\n\n" +
                    "Silakan login dengan username dan password Anda.",
                    "Registrasi Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Close register window
                dispose();
                
            } else {
                throw new ValidationException("Gagal menyimpan data! Coba lagi.");
            }
            
        } catch (ValidationException ex) {
            lblStatus.setText(ex.getMessage());
            lblStatus.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
            lblStatus.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Generate User ID otomatis
     */
    private String generateUserId() {
        int userCount = dbManager.getUserDAO().findAll().size();
        int nextId = userCount + 1;
        return String.format("USR%03d", nextId);
    }
}