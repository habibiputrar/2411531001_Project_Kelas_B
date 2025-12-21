package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.*;
import manager.*;
import exception.*;

// Dashboard frame dengan styling konsisten
// Author: Habibi Putra Rizqullah (2411531001)
public class DashboardFrame extends JFrame {
    
    // Color scheme aplikasi
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color DANGER_COLOR = new Color(220, 20, 60);
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color WHITE = Color.WHITE;
    
    private User currentUser;
    private DatabaseManager dbManager;
    private ScheduleManager scheduleManager;
    private BookingManager bookingManager;
    
    private JTabbedPane tabbedPane;
    private JTable tableSchedules;
    private JTable tableBookings;
    private DefaultTableModel modelSchedules;
    private DefaultTableModel modelBookings;
    
    public DashboardFrame(User user) {
        this.currentUser = user;
        dbManager = DatabaseManager.getInstance();
        scheduleManager = dbManager.getScheduleManager();
        bookingManager = dbManager.getBookingManager();
        
        setupFrame();
        
        if (currentUser instanceof Admin) {
            initAdminDashboard();
        } else if (currentUser instanceof Customer) {
            initCustomerDashboard();
        }
        
        setVisible(true);
    }
    
    private void setupFrame() {
        setTitle("Dashboard - " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }
    
    // ==================== STYLING UTILITIES ====================
    
    // Buat button dengan styling konsisten
    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 35));
        
        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }
    
    // Buat header panel dengan title
    private JPanel createHeaderPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setForeground(WHITE);
        panel.add(lbl);
        
        return panel;
    }
    
    // Apply styling ke table
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(WHITE);
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(100, 149, 237));
        header.setForeground(WHITE);
        header.setReorderingAllowed(false);
        
        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? WHITE : new Color(245, 250, 255));
                }
                return c;
            }
        });
    }
    
    // ==================== ADMIN DASHBOARD ====================
    
    private void initAdminDashboard() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        
        tabbedPane.addTab("📅 Kelola Jadwal", createAdminSchedulePanel());
        tabbedPane.addTab("📋 Lihat Booking", createAdminBookingPanel());
        tabbedPane.addTab("👤 Profile", createProfilePanel());
        
        add(tabbedPane);
    }
    
    private JPanel createAdminSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel("KELOLA JADWAL PERJALANAN");
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton btnAdd = createStyledButton("➕ Tambah Jadwal", SUCCESS_COLOR);
        JButton btnRefresh = createStyledButton("🔄 Refresh", PRIMARY_COLOR);
        JButton btnDelete = createStyledButton("🗑️ Hapus Jadwal", DANGER_COLOR);
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnDelete);
        
        // Table setup
        String[] columns = {"ID", "Kota Asal", "Kota Tujuan", "Tanggal", "Jam", "Bus", "Harga", "Seat", "Status"};
        modelSchedules = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tableSchedules = new JTable(modelSchedules);
        styleTable(tableSchedules);
        tableSchedules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tableSchedules);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        loadScheduleData();
        
        // Event handlers
        btnAdd.addActionListener(e -> showAddScheduleDialog());
        btnRefresh.addActionListener(e -> loadScheduleData());
        btnDelete.addActionListener(e -> deleteSelectedSchedule());
        
        // Combine panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadScheduleData() {
        modelSchedules.setRowCount(0);
        List<Schedule> schedules = scheduleManager.getAllSchedules();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (Schedule schedule : schedules) {
            Object[] row = {
                schedule.getScheduleId(),
                schedule.getKotaAsal(),
                schedule.getKotaTujuan(),
                schedule.getTanggalBerangkat().format(dateFormatter),
                schedule.getJamBerangkat().format(timeFormatter),
                schedule.getBus().getPlatNomor() + " (" + schedule.getBus().getTipeBus() + ")",
                String.format("Rp %,.0f", schedule.getHarga()),
                schedule.getSeatTersedia(),
                schedule.getStatus()
            };
            modelSchedules.addRow(row);
        }
    }
    
    private void showAddScheduleDialog() {
        JDialog dialog = new JDialog(this, "Tambah Jadwal Baru", true);
        dialog.setSize(550, 550);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        JLabel lblTitle = new JLabel("FORM JADWAL BARU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(WHITE);
        headerPanel.add(lblTitle);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Bus selection
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Bus:"), gbc);
        
        List<Bus> buses = dbManager.getBusDAO().findAllActive();
        JComboBox<String> cmbBus = new JComboBox<>();
        for (Bus bus : buses) {
            cmbBus.addItem(bus.getBusId() + " - " + bus.getPlatNomor() + " (" + bus.getTipeBus() + ")");
        }
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(cmbBus, gbc);
        
        // Kota Asal
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Kota Asal:"), gbc);
        JTextField txtKotaAsal = new JTextField(20);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtKotaAsal, gbc);
        
        // Kota Tujuan
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Kota Tujuan:"), gbc);
        JTextField txtKotaTujuan = new JTextField(20);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtKotaTujuan, gbc);
        
        // Tanggal
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(new JLabel("Tanggal (YYYY-MM-DD):"), gbc);
        JTextField txtTanggal = new JTextField(20);
        txtTanggal.setText(LocalDate.now().plusDays(1).toString());
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtTanggal, gbc);
        
        // Jam
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Jam (HH:MM):"), gbc);
        JTextField txtJam = new JTextField(20);
        txtJam.setText("08:00");
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtJam, gbc);
        
        // Harga
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        formPanel.add(new JLabel("Harga:"), gbc);
        JTextField txtHarga = new JTextField(20);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtHarga, gbc);
        
        // Seat
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        formPanel.add(new JLabel("Jumlah Seat:"), gbc);
        JTextField txtSeat = new JTextField(20);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtSeat, gbc);
        
        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        btnPanel.setBackground(BACKGROUND_COLOR);
        
        JButton btnSave = createStyledButton("💾 Simpan", SUCCESS_COLOR);
        JButton btnCancel = createStyledButton("❌ Batal", DANGER_COLOR);
        
        btnSave.addActionListener(e -> {
            try {
                String busSelection = (String) cmbBus.getSelectedItem();
                String busId = busSelection.split(" - ")[0];
                
                String scheduleId = scheduleManager.generateScheduleId();
                
                boolean success = scheduleManager.createSchedule(
                    scheduleId, busId,
                    txtKotaAsal.getText().trim(),
                    txtKotaTujuan.getText().trim(),
                    LocalDate.parse(txtTanggal.getText().trim()),
                    LocalTime.parse(txtJam.getText().trim()),
                    Double.parseDouble(txtHarga.getText().trim()),
                    Integer.parseInt(txtSeat.getText().trim())
                );
                
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "✓ Jadwal berhasil ditambahkan!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadScheduleData();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "✗ Gagal menambahkan jadwal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private void deleteSelectedSchedule() {
        int selectedRow = tableSchedules.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih jadwal yang akan dihapus!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String scheduleId = (String) modelSchedules.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus jadwal ini?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = scheduleManager.deleteSchedule(scheduleId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "✓ Jadwal berhasil dihapus!");
                    loadScheduleData();
                } else {
                    JOptionPane.showMessageDialog(this, "✗ Gagal menghapus jadwal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private JPanel createAdminBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel("SEMUA BOOKING");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        JButton btnRefresh = createStyledButton("🔄 Refresh", PRIMARY_COLOR);
        buttonPanel.add(btnRefresh);
        
        String[] columns = {"Booking ID", "Customer", "Rute", "Tanggal", "Penumpang", "Seat", "Total", "Status"};
        modelBookings = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tableBookings = new JTable(modelBookings);
        styleTable(tableBookings);
        JScrollPane scrollPane = new JScrollPane(tableBookings);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        loadAllBookingsData();
        btnRefresh.addActionListener(e -> loadAllBookingsData());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadAllBookingsData() {
        modelBookings.setRowCount(0);
        List<Booking> bookings = bookingManager.getAllBookings();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        for (Booking booking : bookings) {
            String rute = booking.getSchedule().getKotaAsal() + " → " + booking.getSchedule().getKotaTujuan();
            Object[] row = {
                booking.getBookingId(),
                booking.getCustomer().getFullName(),
                rute,
                booking.getSchedule().getTanggalBerangkat().format(dateFormatter),
                booking.getPassenger().getNama(),
                booking.getJumlahSeat(),
                String.format("Rp %,.0f", booking.getTotalHarga()),
                booking.getStatusBayar()
            };
            modelBookings.addRow(row);
        }
    }
    
    // ==================== CUSTOMER DASHBOARD ====================
    
    private void initCustomerDashboard() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        
        tabbedPane.addTab("📅 Lihat Jadwal", createCustomerViewSchedulePanel());
        tabbedPane.addTab("🎫 Pesan Tiket", createCustomerBookingPanel());
        tabbedPane.addTab("📋 Booking Saya", createCustomerMyBookingPanel());
        tabbedPane.addTab("👤 Profile", createProfilePanel());
        
        add(tabbedPane);
    }
    
    private JPanel createCustomerViewSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel("JADWAL PERJALANAN TERSEDIA");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        JButton btnRefresh = createStyledButton("🔄 Refresh", PRIMARY_COLOR);
        buttonPanel.add(btnRefresh);
        
        String[] columns = {"ID", "Kota Asal", "Kota Tujuan", "Tanggal", "Jam", "Bus", "Harga", "Seat"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        List<Schedule> schedules = scheduleManager.getActiveSchedules();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (Schedule schedule : schedules) {
            Object[] row = {
                schedule.getScheduleId(),
                schedule.getKotaAsal(),
                schedule.getKotaTujuan(),
                schedule.getTanggalBerangkat().format(dateFormatter),
                schedule.getJamBerangkat().format(timeFormatter),
                schedule.getBus().getPlatNomor() + " (" + schedule.getBus().getTipeBus() + ")",
                String.format("Rp %,.0f", schedule.getHarga()),
                schedule.getSeatTersedia()
            };
            model.addRow(row);
        }
        
        btnRefresh.addActionListener(e -> {
            model.setRowCount(0);
            List<Schedule> newSchedules = scheduleManager.getActiveSchedules();
            for (Schedule schedule : newSchedules) {
                Object[] row = {
                    schedule.getScheduleId(),
                    schedule.getKotaAsal(),
                    schedule.getKotaTujuan(),
                    schedule.getTanggalBerangkat().format(dateFormatter),
                    schedule.getJamBerangkat().format(timeFormatter),
                    schedule.getBus().getPlatNomor() + " (" + schedule.getBus().getTipeBus() + ")",
                    String.format("Rp %,.0f", schedule.getHarga()),
                    schedule.getSeatTersedia()
                };
                model.addRow(row);
            }
        });
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCustomerBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel("PESAN TIKET");
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Schedule selection
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblSchedule = new JLabel("Pilih Jadwal:");
        lblSchedule.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(lblSchedule, gbc);
        
        List<Schedule> schedules = scheduleManager.getActiveSchedules();
        JComboBox<String> cmbSchedule = new JComboBox<>();
        for (Schedule schedule : schedules) {
            cmbSchedule.addItem(schedule.getScheduleId() + " - " + 
                schedule.getKotaAsal() + " → " + schedule.getKotaTujuan() + 
                " (" + schedule.getTanggalBerangkat() + ")");
        }
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(cmbSchedule, gbc);
        
        // Nama
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblNama = new JLabel("Nama Penumpang:");
        lblNama.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(lblNama, gbc);
        JTextField txtNama = new JTextField(25);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtNama, gbc);
        
        // KTP
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblKTP = new JLabel("No. KTP (16 digit):");
        lblKTP.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(lblKTP, gbc);
        JTextField txtKTP = new JTextField(25);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtKTP, gbc);
        
        // HP
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblHP = new JLabel("No. HP:");
        lblHP.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(lblHP, gbc);
        JTextField txtHP = new JTextField(25);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtHP, gbc);
        
        // Seat
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        JLabel lblSeat = new JLabel("Jumlah Seat:");
        lblSeat.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(lblSeat, gbc);
        JSpinner spinnerSeat = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(spinnerSeat, gbc);
        
        // Total harga
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        JLabel lblTotalLabel = new JLabel("Total Harga:");
        lblTotalLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(lblTotalLabel, gbc);
        JLabel lblTotal = new JLabel("Rp 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(SUCCESS_COLOR);
        gbc.gridx = 1;
        formPanel.add(lblTotal, gbc);
        
        // Calculate total price
        ActionListener calculateTotal = e -> {
            if (cmbSchedule.getSelectedIndex() != -1 && schedules.size() > 0) {
                Schedule selected = schedules.get(cmbSchedule.getSelectedIndex());
                int seat = (Integer) spinnerSeat.getValue();
                double total = selected.getHarga() * seat;
                lblTotal.setText(String.format("Rp %,.0f", total));
            }
        };
        cmbSchedule.addActionListener(calculateTotal);
        spinnerSeat.addChangeListener(e -> calculateTotal.actionPerformed(null));
        
        if (schedules.size() > 0) {
            calculateTotal.actionPerformed(null);
        }
        
        // Book button
        JButton btnBook = createStyledButton("🎫 PESAN SEKARANG", SUCCESS_COLOR);
        btnBook.setPreferredSize(new Dimension(200, 40));
        btnBook.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnBook.addActionListener(e -> {
            try {
                if (cmbSchedule.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih jadwal terlebih dahulu!");
                    return;
                }
                
                Schedule selected = schedules.get(cmbSchedule.getSelectedIndex());
                Customer customer = (Customer) currentUser;
                
                Booking booking = bookingManager.createBooking(
                    customer, selected,
                    txtNama.getText().trim(),
                    txtKTP.getText().trim(),
                    txtHP.getText().trim(),
                    (Integer) spinnerSeat.getValue()
                );
                
                JOptionPane.showMessageDialog(this, 
                    "✓ Booking berhasil!\n\nBooking ID: " + booking.getBookingId() + 
                    "\nTotal: Rp " + String.format("%,.0f", booking.getTotalHarga()),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                txtNama.setText("");
                txtKTP.setText("");
                txtHP.setText("");
                spinnerSeat.setValue(1);
                
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnBook, gbc);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(BACKGROUND_COLOR);
        wrapperPanel.add(mainPanel, BorderLayout.NORTH);
        
        return wrapperPanel;
    }
    
    private JPanel createCustomerMyBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel("BOOKING SAYA");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        JButton btnRefresh = createStyledButton("🔄 Refresh", PRIMARY_COLOR);
        buttonPanel.add(btnRefresh);
        
        String[] columns = {"Booking ID", "Rute", "Tanggal", "Penumpang", "Seat", "Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        Customer customer = (Customer) currentUser;
        List<Booking> bookings = bookingManager.getBookingsByCustomer(customer.getUserId());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        for (Booking booking : bookings) {
            String rute = booking.getSchedule().getKotaAsal() + " → " + booking.getSchedule().getKotaTujuan();
            Object[] row = {
                booking.getBookingId(),
                rute,
                booking.getSchedule().getTanggalBerangkat().format(dateFormatter),
                booking.getPassenger().getNama(),
                booking.getJumlahSeat(),
                String.format("Rp %,.0f", booking.getTotalHarga()),
                booking.getStatusBayar()
            };
            model.addRow(row);
        }
        
        btnRefresh.addActionListener(e -> {
            model.setRowCount(0);
            List<Booking> newBookings = bookingManager.getBookingsByCustomer(customer.getUserId());
            for (Booking booking : newBookings) {
                String rute = booking.getSchedule().getKotaAsal() + " → " + booking.getSchedule().getKotaTujuan();
                Object[] row = {
                    booking.getBookingId(),
                    rute,
                    booking.getSchedule().getTanggalBerangkat().format(dateFormatter),
                    booking.getPassenger().getNama(),
                    booking.getJumlahSeat(),
                    String.format("Rp %,.0f", booking.getTotalHarga()),
                    booking.getStatusBayar()
                };
                model.addRow(row);
            }
        });
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ==================== PROFILE PANEL ====================
    
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel("PROFILE SAYA");
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        infoPanel.setBackground(WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font valueFont = new Font("Arial", Font.PLAIN, 14);
        
        JLabel lbl1 = new JLabel("User ID:");
        lbl1.setFont(labelFont);
        infoPanel.add(lbl1);
        JLabel val1 = new JLabel(currentUser.getUserId());
        val1.setFont(valueFont);
        infoPanel.add(val1);
        
        JLabel lbl2 = new JLabel("Username:");
        lbl2.setFont(labelFont);
        infoPanel.add(lbl2);
        JLabel val2 = new JLabel(currentUser.getUsername());
        val2.setFont(valueFont);
        infoPanel.add(val2);
        
        JLabel lbl3 = new JLabel("Nama Lengkap:");
        lbl3.setFont(labelFont);
        infoPanel.add(lbl3);
        JLabel val3 = new JLabel(currentUser.getFullName());
        val3.setFont(valueFont);
        infoPanel.add(val3);
        
        JLabel lbl4 = new JLabel("Role:");
        lbl4.setFont(labelFont);
        infoPanel.add(lbl4);
        JLabel val4 = new JLabel(currentUser.getRole());
        val4.setFont(valueFont);
        infoPanel.add(val4);
        
        JLabel lbl5 = new JLabel("User Type:");
        lbl5.setFont(labelFont);
        infoPanel.add(lbl5);
        JLabel val5 = new JLabel(currentUser.getUserType());
        val5.setFont(valueFont);
        infoPanel.add(val5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0;
        contentPanel.add(infoPanel, gbc);
        
        JButton btnLogout = createStyledButton("🚪 Logout", DANGER_COLOR);
        btnLogout.setPreferredSize(new Dimension(150, 40));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin logout?", 
                "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new MainApp();
            }
        });
        
        gbc.gridy = 1;
        gbc.insets = new Insets(25, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(btnLogout, gbc);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
}