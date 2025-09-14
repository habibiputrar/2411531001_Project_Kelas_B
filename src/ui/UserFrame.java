package ui;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import Table.TableUser;
import model.User;

public class UserFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtPassword;
    
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCancel;
    
    private JTable tableUser;
    private TableUser tableModel;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserFrame frame = new UserFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public UserFrame() {
        setTitle("USERS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(new Color(214, 217, 223));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 15));
        
        JPanel topPanel = createFormPanel();
        contentPane.add(topPanel, BorderLayout.NORTH);
        
        JPanel bottomPanel = createTablePanel();
        contentPane.add(bottomPanel, BorderLayout.CENTER);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setBorder(new LineBorder(Color.GRAY, 1, true));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(410, 160));
        
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]{90, 280, 0};
        gbl.rowHeights = new int[]{30, 30, 30, 45, 0};
        gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        formPanel.setLayout(gbl);
        
        JLabel lblName = new JLabel("Name");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.WEST;
        gbc_lblName.insets = new Insets(15, 20, 8, 15);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 0;
        formPanel.add(lblName, gbc_lblName);
        
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(200, 22));
        GridBagConstraints gbc_txtName = new GridBagConstraints();
        gbc_txtName.insets = new Insets(15, 0, 8, 20);
        gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtName.gridx = 1;
        gbc_txtName.gridy = 0;
        formPanel.add(txtName, gbc_txtName);
        
        JLabel lblUsername = new JLabel("Username");
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.insets = new Insets(0, 20, 8, 15);
        gbc_lblUsername.gridx = 0;
        gbc_lblUsername.gridy = 1;
        formPanel.add(lblUsername, gbc_lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(200, 22));
        GridBagConstraints gbc_txtUsername = new GridBagConstraints();
        gbc_txtUsername.insets = new Insets(0, 0, 8, 20);
        gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUsername.gridx = 1;
        gbc_txtUsername.gridy = 1;
        formPanel.add(txtUsername, gbc_txtUsername);
        
        JLabel lblPassword = new JLabel("Password");
        GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.anchor = GridBagConstraints.WEST;
        gbc_lblPassword.insets = new Insets(0, 20, 15, 15);
        gbc_lblPassword.gridx = 0;
        gbc_lblPassword.gridy = 2;
        formPanel.add(lblPassword, gbc_lblPassword);
        
        txtPassword = new JTextField();
        txtPassword.setPreferredSize(new Dimension(200, 22));
        GridBagConstraints gbc_txtPassword = new GridBagConstraints();
        gbc_txtPassword.insets = new Insets(0, 0, 15, 20);
        gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPassword.gridx = 1;
        gbc_txtPassword.gridy = 2;
        formPanel.add(txtPassword, gbc_txtPassword);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        gbc_buttonPanel.gridwidth = 2;
        gbc_buttonPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_buttonPanel.insets = new Insets(0, 15, 15, 15);
        gbc_buttonPanel.gridx = 0;
        gbc_buttonPanel.gridy = 3;
        formPanel.add(buttonPanel, gbc_buttonPanel);
        
        btnSave = new JButton("Save");
        btnSave.setPreferredSize(new Dimension(75, 30));
        btnSave.setBackground(new Color(0, 128, 0));
        btnSave.setForeground(new Color(0, 0, 0));
        buttonPanel.add(btnSave);
        
        btnUpdate = new JButton("Update");
        btnUpdate.setPreferredSize(new Dimension(75, 30));
        btnUpdate.setBackground(new Color(128, 128, 255));
        btnUpdate.setForeground(new Color(0, 0, 0));
        buttonPanel.add(btnUpdate);
        
        btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(new Dimension(75, 30));
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(new Color(0, 0, 0));
        buttonPanel.add(btnDelete);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(75, 30));
        btnCancel.setBackground(Color.YELLOW);
        btnCancel.setForeground(Color.BLACK);
        buttonPanel.add(btnCancel);
        
        return formPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new LineBorder(Color.GRAY, 1, true));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BorderLayout());
        
        List<User> userList = new ArrayList<>();
        tableModel = new TableUser(userList);
        
        tableUser = new JTable(tableModel);
        tableUser.setBackground(Color.WHITE);
        tableUser.setRowHeight(20);

        tablePanel.add(tableUser.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(tableUser, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    public JTextField getTxtName() {
        return txtName;
    }
    
    public JTextField getTxtUsername() {
        return txtUsername;
    }
    
    public JTextField getTxtPassword() {
        return txtPassword;
    }
    
    public JButton getBtnSave() {
        return btnSave;
    }
    
    public JButton getBtnUpdate() {
        return btnUpdate;
    }
    
    public JButton getBtnDelete() {
        return btnDelete;
    }
    
    public JButton getBtnCancel() {
        return btnCancel;
    }
    
    public JTable getTableUser() {
        return tableUser;
    }
    
    public TableUser getTableModel() {
        return tableModel;
    }
}