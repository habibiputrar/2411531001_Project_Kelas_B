package Pratikum5;

public class Pesawat extends Kendaraan implements TransportasiUdara {
    private String jenisPesawat;
    private String maskapai;


    public Pesawat(String merk, String model, int tahunProduksi, String jenisPesawat, String maskapai, int kapasitasPenumpang) {
        super(merk, model, tahunProduksi);
        this.jenisPesawat = jenisPesawat;
        this.maskapai = maskapai;

    }

    @Override
    public void jenisPenerbangan() {
        System.out.println("Jenis Penerbangan: " + jenisPesawat);
    }

    @Override
    public String namaMaskapai() {
        return maskapai;
    }

    @Override
    public void infoKonsumsi() {
        System.out.println("Info Konsumsi: Konsumsi bahan bakar tergantung jarak tempuh dan ukuran pesawat");
    }

    public void nyalakanMesin() {
        System.out.println("Nyalakan Mesin : Bersiap Lepas Landa");
    }
    
    @Override
    public String jenisBahanBakar() {
        return "Avtur";
    }


}
