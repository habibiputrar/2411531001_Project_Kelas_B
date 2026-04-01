package Pratikum5;

public class Bus extends Kendaraan implements TransportasiUmum {

 private String kelasBus;
 private int kapasitas;

 public Bus(String merk, String model, int tahunProduksi, String kelasBus, int kapasitas) {
     super(merk, model, tahunProduksi);
     this.kelasBus = kelasBus;
     this.kapasitas = kapasitas;
 }

 @Override
 public void nyalakanMesin() {
     System.out.println("Nyalakan mesin : Putar kunci untuk menyalakan.");
 }

 @Override
 public String jenisBahanBakar() {
     return "Solar";
 }

 @Override
 public int kapasitasPenumpang() {
     return kapasitas;
 }

 public void fiturBus() {
     System.out.println("Fitur bus      : Dilengkapi kursi nyaman dan fasilitas hiburan.");
 }

 public class JadwalPerjalanan {
     private String rute;
     private String waktuBerangkat;

     public JadwalPerjalanan(String rute, String waktuBerangkat) {
         this.rute = rute;
         this.waktuBerangkat = waktuBerangkat;
     }

     public void tampilkanJadwal() {
         System.out.println("Jadwal Perjalanan Bus : Rute " + rute + " , Waktu Berangkat  :" + waktuBerangkat);
        
     }
 }


 public void tampilkanDetailBus() {
     tampilkanInfo(); 
     System.out.println("Kelas Bus          : " + kelasBus);
     System.out.println("Jenis Bahan Bakar  : " + jenisBahanBakar());
     System.out.println("Kapasitas Penumpang: " + kapasitasPenumpang());
 }


}

