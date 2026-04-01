package Tugas;

public class Service {
    String id, jenis, status;
    double harga;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static void main(String args[]) {
        Service layanan = new Service();
        layanan.setId("S001");
        layanan.setJenis("Dry Clean");
        layanan.setHarga(10000.0);
        layanan.setStatus("Tersedia");
        
        System.out.println("Layanan " + layanan.getJenis() 
            + " dengan kode " + layanan.getId() 
            + " tersedia dengan tarif Rp" + layanan.getHarga() 
            + " status " + layanan.getStatus());
    }
}