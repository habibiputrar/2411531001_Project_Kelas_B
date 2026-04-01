package model;

// Model class untuk data bus
// Menerapkan ENCAPSULATION
// Author: Habibi Putra Rizqullah (2411531001)
public class Bus {
    // ENCAPSULATION: atribut private
    private String busId;
    private String platNomor;
    private String tipeBus;
    private int kapasitas;
    private String status;

    public Bus() {
    }

    public Bus(String busId, String platNomor, String tipeBus, int kapasitas, String status) {
        this.busId = busId;
        this.platNomor = platNomor;
        this.tipeBus = tipeBus;
        this.kapasitas = kapasitas;
        this.status = status;
    }

    // ENCAPSULATION: getter dan setter
    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getTipeBus() {
        return tipeBus;
    }

    public void setTipeBus(String tipeBus) {
        this.tipeBus = tipeBus;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Cek apakah bus sedang aktif
    public boolean isAktif() {
        return "AKTIF".equalsIgnoreCase(status);
    }

    // Display info bus
    public String displayInfo() {
        return "Bus ID: " + busId + "\n" +
               "Plat Nomor: " + platNomor + "\n" +
               "Tipe Bus: " + tipeBus + "\n" +
               "Kapasitas: " + kapasitas + " seat\n" +
               "Status: " + status;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId='" + busId + '\'' +
                ", platNomor='" + platNomor + '\'' +
                ", tipeBus='" + tipeBus + '\'' +
                ", kapasitas=" + kapasitas +
                ", status='" + status + '\'' +
                '}';
    }
}