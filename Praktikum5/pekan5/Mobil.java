package pekan5;

public final class Mobil extends Kendaraan implements BahanBakar {
    private String jenisTransmisi;

    public Mobil(String merk, String model, int tahunProduksi, String jenisTransmisi) {
        super(merk, model, tahunProduksi);
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public void nyalakanMesin() {
        System.out.println("Menyalakan mesin mobil dengan menekan tombol start.");
    }

    @Override
    public String jenisBahanBakar() {
        return "Bensin";
    }

    public void fiturMobil() {
        System.out.println("Mobil dilengkapi dengan AC otomatis dan sistem navigasi GPS.");
    }
}
