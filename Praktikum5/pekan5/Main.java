package Pratikum5;

public class Main {
    public static void main(String[] args) {

        Mobil mobil = new Mobil("Toyota", "Avanza", 2021, null);
        
        mobil.tampilkanInfo();
        mobil.nyalakanMesin();
        System.out.println("Bahan Bakar    :" + mobil.jenisBahanBakar());
        mobil.infoKonsumsi();
        mobil.fiturMobil();

        System.out.println();

        Bus bus = new Bus("Mercedes–Benz", "Bus Pariwisata", 2018, null, 45);
        bus.tampilkanInfo();
        bus.nyalakanMesin();
        System.out.println("Bahan Bakar    : " + bus.jenisBahanBakar());
        bus.infoKonsumsi();
        bus.tampilkanInfo();
        bus.fiturBus();

        Bus.JadwalPerjalanan jadwal = bus.new JadwalPerjalanan("Jakarta – Bandung", " 08:00");
        jadwal.tampilkanJadwal();
        
        System.out.println();

        Pesawat pesawat = new Pesawat("Boeing", "737 ", 2022, "Domestik", "Garuda Indonesia", 180);
        pesawat.tampilkanInfo();
        pesawat.nyalakanMesin();
        System.out.println("Bahan Bakar    : " + pesawat.jenisBahanBakar());
        pesawat.jenisPenerbangan();
        System.out.println("Maskapai       : " + pesawat.namaMaskapai());

    }
}
