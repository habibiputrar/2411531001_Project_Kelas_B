package pekan5;

public class Bus extends Kendaraan implements TransportasiUmum {
    private String kelasBus;

    public Bus(String merk, String model, int tahunProduksi, String kelasBus) {
        super(merk, model, tahunProduksi);
        this.kelasBus = kelasBus;
    }

    @Override
    public void nyalakanMesin() {
        System.out.println("Menyalakan mesin bus dengan memutar kunci kontak besar.");
    }

    @Override
    public String jenisBahanBakar() {
        return "Solar";
    }

    @Override
    public int kapasitasPenumpang() {
        return 50;
    }

    public void fiturBus() {
        System.out.println("Bus memiliki kursi yang dapat direbahkan dan toilet di dalam.");
    }

    public class JadwalPerjalanan {
        private String rute;
        private String waktuBerangkat;

        public JadwalPerjalanan(String rute, String waktuBerangkat) {
            this.rute = rute;
            this.waktuBerangkat = waktuBerangkat;
        }

        public void tampilkanJadwal() {
            System.out.println("Rute: " + rute);
            System.out.println("Waktu Berangkat: " + waktuBerangkat);
        }
    }
}
