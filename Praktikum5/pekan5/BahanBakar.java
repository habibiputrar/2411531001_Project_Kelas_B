package pekan5;

public interface BahanBakar {
    String jenisBahanBakar();

    default void infoKonsumsi() {
        System.out.println("Konsumsi bahan bakar tergantung kapasitas mesin.");
    }
}
