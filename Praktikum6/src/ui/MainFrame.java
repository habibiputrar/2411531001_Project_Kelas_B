package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Pesanan");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(138, 119, 116, 62);
		contentPane.add(btnNewButton);
		
		JButton btnLayanan = new JButton("Layanan");
		btnLayanan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLayanan.setBounds(277, 119, 116, 62);
		contentPane.add(btnLayanan);
		
		JButton btnPelanggan = new JButton("Pelanggan");
		btnPelanggan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPelanggan.setBounds(416, 119, 116, 62);
		contentPane.add(btnPelanggan);
		
		JButton btnPengguna = new JButton("Pengguna");
		btnPengguna.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPengguna.setBounds(138, 206, 116, 62);
		contentPane.add(btnPengguna);
		
		JButton btnLaporan = new JButton("Laporan");
		btnLaporan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLaporan.setBounds(277, 206, 116, 62);
		contentPane.add(btnLaporan);
		
		JButton btnProfile = new JButton("Profile");
		btnProfile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnProfile.setBounds(416, 206, 116, 62);
		contentPane.add(btnProfile);
		
		JButton btnKeluar = new JButton("Keluar");
		btnKeluar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnKeluar.setBounds(138, 278, 394, 62);
		contentPane.add(btnKeluar);
	}

}