package pekan5;

public class Main {
    public static void main(String[] args) {
        Mobil mobil = new Mobil("Toyota", "Avanza", 2021, "Bensin", "Automatic");
        Bus bus = new Bus("Mercedes-Benz", "Bus Pariwisata", 2018, "Solar", "Eksekutif", 45);
        Bus.JadwalPerjalanan jadwal = bus.new JadwalPerjalanan("Rute Jakarta – Bandung", "08:00");
        Pesawat pesawat = new Pesawat("Garuda", "Boeing 737", 100, "Avtur", "Domestik");

        System.out.println("Merk: " + mobil.getMerk());
        System.out.println("Model: " + mobil.getModel());
        System.out.println("Tahun Produksi: " + mobil.getTahunProduksi());
        mobil.nyalakanMesin();
        System.out.println("Jenis Bahan Bakar: " + mobil.jenisBahanBakar());
        mobil.infoKonsumsi();
        mobil.fiturMobil();
        System.out.println();

        System.out.println("Merk: " + bus.getMerk());
        System.out.println("Model: " + bus.getModel());
        System.out.println("Tahun Produksi: " + bus.getTahunProduksi());
        bus.nyalakanMesin();
        System.out.println("Jenis Bahan Bakar: " + bus.jenisBahanBakar());
        bus.infoKonsumsi();
        System.out.println("Kapasitas Penumpang: " + bus.kapasitasPenumpang() + " penumpang");
        bus.fiturBus();
        jadwal.tampilkanJadwal();
        System.out.println();

        System.out.println("Merk: " + pesawat.getMerk());
        System.out.println("Model: " + pesawat.getModel());
        System.out.println("Tahun Produksi: " + pesawat.getTahunProduksi());
        pesawat.nyalakanMesin();
        System.out.println("Jenis Bahan Bakar: " + pesawat.jenisBahanBakar());
    }
}
