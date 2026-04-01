package Praktikum9.Tugas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DownloadManagerGUI extends JFrame {

    public DownloadManagerGUI() {
        setTitle("Download Manager App");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(242, 242, 242));

        JLabel title = new JLabel("Download Manager App", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 20, 15));
        centerPanel.setBackground(new Color(242, 242, 242));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));

        JLabel file1 = new JLabel("File 1");
        JLabel file2 = new JLabel("File 2");
        JLabel file3 = new JLabel("File 3");

        file1.setFont(new Font("Arial", Font.PLAIN, 16));
        file2.setFont(new Font("Arial", Font.PLAIN, 16));
        file3.setFont(new Font("Arial", Font.PLAIN, 16));

        Color blueColor = new Color(62, 123, 250);

        JProgressBar pb1 = new JProgressBar();
        pb1.setValue(0);
        pb1.setForeground(blueColor);

        JProgressBar pb2 = new JProgressBar();
        pb2.setValue(0);
        pb2.setForeground(blueColor);

        JProgressBar pb3 = new JProgressBar();
        pb3.setValue(0);
        pb3.setForeground(blueColor);

        centerPanel.add(file1); centerPanel.add(pb1);
        centerPanel.add(file2); centerPanel.add(pb2);
        centerPanel.add(file3); centerPanel.add(pb3);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setPreferredSize(new Dimension(700, 80));
        buttonPanel.setBackground(new Color(242, 242, 242));

        JButton downloadBtn = new JButton("Downloading");
        downloadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startAllDownloads(pb1, pb2, pb3, downloadBtn);
            }
        });
        downloadBtn.setBackground(new Color(192, 192, 192));
        downloadBtn.setFont(new Font("Arial", Font.PLAIN, 16));

        downloadBtn.setBounds(482, 10, 150, 40);

        buttonPanel.add(downloadBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }
    
    private void startAllDownloads(JProgressBar pb1, JProgressBar pb2, 
                                   JProgressBar pb3, JButton btn) {
        btn.setEnabled(false);
        btn.setText("Downloading...");
        
        pb1.setValue(0);
        pb2.setValue(0);
        pb3.setValue(0);
        
        System.out.println("\nDownloading...\n");
        
        DownloadTask task1 = new DownloadTask("File-1", pb1, 300);
        DownloadTask task2 = new DownloadTask("File-2", pb2, 400);
        DownloadTask task3 = new DownloadTask("File-3", pb3, 600);
        
        task1.startDownload();
        task2.startDownload();
        task3.startDownload();
        
        new Thread(() -> {
            try {
                Thread.sleep(13000);
                SwingUtilities.invokeLater(() -> {
                    btn.setEnabled(true);
                    btn.setText("Start Again");
                    System.out.println("\nSemua file selesai diunduh!");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DownloadManagerGUI().setVisible(true));
    }
}