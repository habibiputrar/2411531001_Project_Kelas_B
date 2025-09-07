package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Point offset = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainFrame() {
        setTitle("Laundry Apps");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);

        contentPane = new JPanel(new BorderLayout(0, 0));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        JPanel panelHeader = new JPanel(null);
        panelHeader.setPreferredSize(new Dimension(600, 60));
        panelHeader.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Laundry Apps");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(255, 20, 147));
        lblTitle.setBounds(47, 10, 300, 40);
        panelHeader.add(lblTitle);

        lblTitle.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                offset = e.getPoint();
            }
        });

        lblTitle.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point newPoint = SwingUtilities.convertPoint(lblTitle, e.getPoint(), panelHeader);
                lblTitle.setLocation(newPoint.x - offset.x, newPoint.y - offset.y);
            }
        });

        contentPane.add(panelHeader, BorderLayout.NORTH);

        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(Color.WHITE);
        panelMenu.setLayout(new GridLayout(2, 3, 20, 20));
        panelMenu.setBorder(new EmptyBorder(10, 50, 10, 50));

        JButton btnPesanan = new JButton("PESANAN");
        btnPesanan.setBackground(Color.LIGHT_GRAY);
        panelMenu.add(btnPesanan);

        JButton btnLayanan = new JButton("LAYANAN");
        btnLayanan.setBackground(Color.LIGHT_GRAY);
        panelMenu.add(btnLayanan);

        JButton btnPelanggan = new JButton("PELANGGAN");
        btnPelanggan.setBackground(Color.LIGHT_GRAY);
        panelMenu.add(btnPelanggan);

        JButton btnPengguna = new JButton("PENGGUNA");
        btnPengguna.setBackground(Color.LIGHT_GRAY);
        panelMenu.add(btnPengguna);

        JButton btnLaporan = new JButton("LAPORAN");
        btnLaporan.setBackground(Color.LIGHT_GRAY);
        panelMenu.add(btnLaporan);

        JButton btnProfile = new JButton("PROFILE");
        btnProfile.setBackground(Color.LIGHT_GRAY);
        panelMenu.add(btnProfile);

        contentPane.add(panelMenu, BorderLayout.CENTER);

        JPanel panelKeluar = new JPanel(new BorderLayout());
        panelKeluar.setBackground(Color.WHITE);
        panelKeluar.setBorder(new EmptyBorder(20, 50, 20, 50));

        JButton btnKeluar = new JButton("KELUAR");
        btnKeluar.setPreferredSize(new Dimension(300, 40));
        btnKeluar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnKeluar.setBackground(Color.LIGHT_GRAY);
        panelKeluar.add(btnKeluar, BorderLayout.CENTER);

        contentPane.add(panelKeluar, BorderLayout.SOUTH);
    }
}
