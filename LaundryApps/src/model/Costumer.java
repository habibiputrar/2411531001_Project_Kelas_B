package model;

public class Costumer {
    String id, nama, alamat, nomorHp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public void setNomorHp(String nomorHp) {
        this.nomorHp = nomorHp;
    }
    
    public static void main(String args[]) {
        Costumer cost = new Costumer();
        cost.setId("C001");
        cost.setNama("Habibi");
        cost.setAlamat("Jl. Limau Manis");
        cost.setNomorHp("082173241590");
        
        System.out.println("Customer dengan ID " 
                + cost.getId() + " bernama " + cost.getNama() 
                + " tinggal di " + cost.getAlamat() + " dengan nomor HP " + cost.getNomorHp());
        }
}