package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

import DAO.CostumerRepo;
import model.Costumer;
import model.CostumerBuilder;
import Table.TableCostumer;

public class CostumerFrame extends JFrame {

    private JPanel contentPane;
    private JTextField txtNama, txtEmail, txtAlamat, txtHp;
    private JButton btnSimpan, btnBatal;
    private JTable tableCostumer;
    private TableCostumer tableModel;

    private CostumerRepo repo = new CostumerRepo();
    private List<Costumer> listCostumer;
    private String id = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CostumerFrame frame = new CostumerFrame();
                frame.setVisible(true);
                frame.loadTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CostumerFrame() {
        setTitle("DATA COSTUMER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 550);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout(0, 15));
        setContentPane(contentPane);

        contentPane.add(createFormPanel(), BorderLayout.NORTH);
        contentPane.add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setBorder(new LineBorder(Color.GRAY, 1, true));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(460, 230));

        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]{90, 280, 0};
        gbl.rowHeights = new int[]{30, 30, 30, 30, 45, 0};
        gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        formPanel.setLayout(gbl);

        JLabel lblNama = new JLabel("Nama");
        GridBagConstraints gbc_lblNama = new GridBagConstraints();
        gbc_lblNama.anchor = GridBagConstraints.WEST;
        gbc_lblNama.insets = new Insets(15, 20, 8, 15);
        gbc_lblNama.gridx = 0;
        gbc_lblNama.gridy = 0;
        formPanel.add(lblNama, gbc_lblNama);

        txtNama = new JTextField();
        GridBagConstraints gbc_txtNama = new GridBagConstraints();
        gbc_txtNama.insets = new Insets(15, 0, 8, 20);
        gbc_txtNama.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNama.gridx = 1;
        gbc_txtNama.gridy = 0;
        formPanel.add(txtNama, gbc_txtNama);

        JLabel lblEmail = new JLabel("Email");
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.anchor = GridBagConstraints.WEST;
        gbc_lblEmail.insets = new Insets(0, 20, 8, 15);
        gbc_lblEmail.gridx = 0;
        gbc_lblEmail.gridy = 1;
        formPanel.add(lblEmail, gbc_lblEmail);

        txtEmail = new JTextField();
        GridBagConstraints gbc_txtEmail = new GridBagConstraints();
        gbc_txtEmail.insets = new Insets(0, 0, 8, 20);
        gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtEmail.gridx = 1;
        gbc_txtEmail.gridy = 1;
        formPanel.add(txtEmail, gbc_txtEmail);

        JLabel lblAlamat = new JLabel("Alamat");
        GridBagConstraints gbc_lblAlamat = new GridBagConstraints();
        gbc_lblAlamat.anchor = GridBagConstraints.WEST;
        gbc_lblAlamat.insets = new Insets(0, 20, 8, 15);
        gbc_lblAlamat.gridx = 0;
        gbc_lblAlamat.gridy = 2;
        formPanel.add(lblAlamat, gbc_lblAlamat);

        txtAlamat = new JTextField();
        GridBagConstraints gbc_txtAlamat = new GridBagConstraints();
        gbc_txtAlamat.insets = new Insets(0, 0, 8, 20);
        gbc_txtAlamat.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtAlamat.gridx = 1;
        gbc_txtAlamat.gridy = 2;
        formPanel.add(txtAlamat, gbc_txtAlamat);

        JLabel lblHp = new JLabel("HP");
        GridBagConstraints gbc_lblHp = new GridBagConstraints();
        gbc_lblHp.anchor = GridBagConstraints.WEST;
        gbc_lblHp.insets = new Insets(0, 20, 8, 15);
        gbc_lblHp.gridx = 0;
        gbc_lblHp.gridy = 3;
        formPanel.add(lblHp, gbc_lblHp);

        txtHp = new JTextField();
        GridBagConstraints gbc_txtHp = new GridBagConstraints();
        gbc_txtHp.insets = new Insets(0, 0, 8, 20);
        gbc_txtHp.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtHp.gridx = 1;
        gbc_txtHp.gridy = 3;
        formPanel.add(txtHp, gbc_txtHp);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        gbc_buttonPanel.gridwidth = 2;
        gbc_buttonPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_buttonPanel.insets = new Insets(0, 15, 15, 15);
        gbc_buttonPanel.gridx = 0;
        gbc_buttonPanel.gridy = 4;
        formPanel.add(buttonPanel, gbc_buttonPanel);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnSimpan.setBackground(new Color(128, 0, 0));
        btnSimpan.setForeground(new Color(255, 255, 255));
        btnSimpan.setPreferredSize(new Dimension(90, 30));
        buttonPanel.add(btnSimpan);

        btnBatal = new JButton("Batal");
        btnBatal.setBackground(new Color(192, 192, 192));
        btnBatal.setPreferredSize(new Dimension(90, 30));
        buttonPanel.add(btnBatal);

        btnSimpan.addActionListener(e -> saveData());
        btnBatal.addActionListener(e -> batalAction());

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.setLayout(new BorderLayout());

        listCostumer = repo.show();
        tableModel = new TableCostumer(listCostumer);

        tableCostumer = new JTable(tableModel);
        tableCostumer.setRowHeight(22);

        tableCostumer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableCostumer.getSelectedRow();
                id = tableCostumer.getValueAt(row, 0).toString();
                txtNama.setText(tableCostumer.getValueAt(row, 1).toString());
                txtEmail.setText(tableCostumer.getValueAt(row, 2).toString());
                txtAlamat.setText(tableCostumer.getValueAt(row, 3).toString());
                txtHp.setText(tableCostumer.getValueAt(row, 4).toString());
            }
        });

        panel.add(new JScrollPane(tableCostumer), BorderLayout.CENTER);
        return panel;
    }

    private void saveData() {
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama wajib diisi!");
            return;
        }

        String newId = String.valueOf(System.currentTimeMillis());

        Costumer c = new CostumerBuilder()
                .setId(newId)
                .setNama(txtNama.getText())
                .setEmail(txtEmail.getText())
                .setAlamat(txtAlamat.getText())
                .setHp(txtHp.getText())
                .build();

        repo.save(c);
        loadTable();
        resetForm();
    }

    private void batalAction() {
        if (id == null) {
            resetForm();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Hapus data ini?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            repo.delete(id);
            loadTable();
        }

        resetForm();
    }

    private void resetForm() {
        txtNama.setText("");
        txtEmail.setText("");
        txtAlamat.setText("");
        txtHp.setText("");
        id = null;
    }

    public void loadTable() {
        listCostumer = repo.show();
        tableModel = new TableCostumer(listCostumer);
        tableCostumer.setModel(tableModel);
    }
}
