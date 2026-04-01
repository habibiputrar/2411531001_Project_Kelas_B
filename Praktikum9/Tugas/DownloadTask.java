package Praktikum9.Tugas;

import javax.swing.*;

class DownloadTask {
    private String fileName;
    private JProgressBar progressBar;
    private int speed;
    
    public DownloadTask(String fileName, JProgressBar progressBar, int speed) {
        this.fileName = fileName;
        this.progressBar = progressBar;
        this.speed = speed;
    }
    
    public void startDownload() {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i += 5) {
                    progressBar.setValue(i);
                    Thread.sleep(speed);
                }
                System.out.println(fileName + " selesai diunduh!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}