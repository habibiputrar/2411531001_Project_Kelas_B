package Tugas;

public class Order {
    String id, idCustomer, idService, idUser, tanggal, tanggalSelesai, status, statusPembayaran;
    double total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }
    
    public static void main(String args[]) {
        Order pesanan = new Order();
        pesanan.setId("O001");
        pesanan.setIdCustomer("C001");
        pesanan.setIdService("S001");
        pesanan.setTotal(10000.0);
        pesanan.setStatus("Selesai");
        pesanan.setStatusPembayaran("Lunas");
        
        System.out.println("Pesanan nomor " + pesanan.getId() 
            + " dari customer " + pesanan.getIdCustomer()
            + " untuk layanan " + pesanan.getIdService()
            + " dengan total biaya Rp" + pesanan.getTotal()
            + " statusnya " + pesanan.getStatus()
            + " dan pembayaran " + pesanan.getStatusPembayaran());
    }
}