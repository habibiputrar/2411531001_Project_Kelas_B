package Praktikum10.Latihan4;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ThreadPoolGUI extends JFrame {
    private JTextField txtThreads;
    private JTextField txtTasks;
    private JButton btnStart;
    private JButton btnClear;
    private JList<String> taskList;
    private DefaultListModel<String> taskListModel;
    private JTextArea logArea;
    private JLabel statusLabel;
    private ExecutorService threadPool;
    
    public ThreadPoolGUI() {
        setTitle("Aplikasi ThreadPool dengan GUI");
        setSize(720, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // ===== PANEL ATAS (PENGATURAN) =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBorder(new TitledBorder("Pengaturan"));
        
        topPanel.add(new JLabel("Jumlah Thread:"));
        txtThreads = new JTextField("3", 5);
        topPanel.add(txtThreads);
        
        topPanel.add(new JLabel("Jumlah Tugas:"));
        txtTasks = new JTextField("20", 5);
        topPanel.add(txtTasks);
        
        btnStart = new JButton("Mulai Proses");
        btnStart.addActionListener(e -> startProcessing());
        topPanel.add(btnStart);
        
        btnClear = new JButton("Bersihkan Log");
        btnClear.addActionListener(e -> clearLog());
        topPanel.add(btnClear);
        
        add(topPanel, BorderLayout.NORTH);
        
        // ===== PANEL TENGAH =====
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // --- STATUS TUGAS ---
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JScrollPane taskScroll = new JScrollPane(taskList);
        taskScroll.setBorder(new TitledBorder("Status Tugas"));
        centerPanel.add(taskScroll);
        
        // --- LOG AKTIVITAS ---
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(new TitledBorder("Log Aktivitas"));
        centerPanel.add(logScroll);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // ===== STATUS BAR BAWAH =====
        statusLabel = new JLabel("Log dibersihkan. Siap untuk proses baru.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    private void startProcessing() {
        try {
            int threadCount = Integer.parseInt(txtThreads.getText());
            int taskCount = Integer.parseInt(txtTasks.getText());
            
            if (threadCount < 1 || taskCount < 1) {
                JOptionPane.showMessageDialog(this,
                    "Jumlah thread dan tugas harus lebih dari 0!",
                    "Input Tidak Valid",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Disable tombol selama proses
            btnStart.setEnabled(false);
            taskListModel.clear();
            logArea.append("=== Memulai Proses Baru ===\n");
            logArea.append("ThreadPool dibuat dengan " + threadCount + " worker threads\n\n");
            
            statusLabel.setText("Memproses " + taskCount + " tugas dengan " + threadCount + " threads...");
            
            // Buat ThreadPool
            threadPool = Executors.newFixedThreadPool(threadCount);
            
            // Tambahkan tugas ke list
            for (int i = 1; i <= taskCount; i++) {
                taskListModel.addElement("Task #" + i + " - Waiting");
            }
            
            // Submit tugas ke ThreadPool
            for (int i = 1; i <= taskCount; i++) {
                Task task = new Task(i, logArea, taskListModel);
                threadPool.execute(task);
            }
            
            // Shutdown dan tunggu selesai di thread terpisah
            new Thread(() -> {
                threadPool.shutdown();
                try {
                    if (threadPool.awaitTermination(5, TimeUnit.MINUTES)) {
                        SwingUtilities.invokeLater(() -> {
                            logArea.append("\n=== Semua tugas selesai ===\n");
                            statusLabel.setText("Semua tugas selesai!");
                            btnStart.setEnabled(true);
                        });
                    }
                } catch (InterruptedException e) {
                    threadPool.shutdownNow();
                }
            }).start();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Masukkan angka yang valid!",
                "Input Tidak Valid",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearLog() {
        logArea.setText("");
        taskListModel.clear();
        statusLabel.setText("Log dibersihkan. Siap untuk proses baru.");
    }
    
    class Task implements Runnable {
        private int taskId;
        private JTextArea logArea;
        private DefaultListModel<String> taskListModel;
        
        public Task(int taskId, JTextArea logArea, DefaultListModel<String> taskListModel) {
            this.taskId = taskId;
            this.logArea = logArea;
            this.taskListModel = taskListModel;
        }
        
        public void run() {
            SwingUtilities.invokeLater(() -> {
                logArea.append("Task #" + taskId + " dimulai oleh " +
                              Thread.currentThread().getName() + "\n");
                updateTaskList("Task #" + taskId + " - Running");
            });
            
            try {
                int processingTime = new Random().nextInt(3000) + 1000;
                Thread.sleep(processingTime);
                
                SwingUtilities.invokeLater(() -> {
                    logArea.append("Task #" + taskId + " selesai oleh " +
                                  Thread.currentThread().getName() +
                                  " (waktu: " + processingTime + "ms)\n");
                    updateTaskList("Task #" + taskId + " - Completed ✓");
                });
            } catch (InterruptedException e) {
                SwingUtilities.invokeLater(() -> {
                    logArea.append("Task #" + taskId + " terganggu!\n");
                });
                Thread.currentThread().interrupt();
            }
        }
        
        private void updateTaskList(String status) {
            for (int i = 0; i < taskListModel.size(); i++) {
                if (taskListModel.get(i).startsWith("Task #" + taskId)) {
                    taskListModel.set(i, status);
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ThreadPoolGUI().setVisible(true);
        });
    }
}
