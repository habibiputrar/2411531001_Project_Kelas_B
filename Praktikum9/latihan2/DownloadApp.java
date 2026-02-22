package Praktikum9.latihan2;

public class DownloadApp {
    public static void main(String[] args) throws InterruptedException {
        Thread f1 = new DownloadTask("File-1");
        Thread f2 = new DownloadTask("File-2");
        Thread f3 = new DownloadTask("File-3");
        
        f1.start();
        f2.start();
        f3.start();
        
        System.out.println("\nDownloading...\n");
        
        f1.join();
        f2.join();
        f3.join();
        
        System.out.println("\nSemua file selesai diunduh!");
        
        System.out.println("\nStatus akhir:");
        System.out.println("Thread-0: TERMINATED");
        System.out.println("Thread-1: TERMINATED");
        System.out.println("Thread-2: TERMINATED");
    }
}