package model;

// Model class untuk data penumpang
// Menerapkan ENCAPSULATION
// Author: Habibi Putra Rizqullah (2411531001)
public class Passenger {
    // ENCAPSULATION: atribut private
    private String passengerId;
    private String nama;
    private String noKTP;
    private String noHP;

    public Passenger() {
    }

    public Passenger(String passengerId, String nama, String noKTP, String noHP) {
        this.passengerId = passengerId;
        this.nama = nama;
        this.noKTP = noKTP;
        this.noHP = noHP;
    }

    // ENCAPSULATION: getter dan setter
    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoKTP() {
        return noKTP;
    }

    public void setNoKTP(String noKTP) {
        this.noKTP = noKTP;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    // Validasi nomor KTP (harus 16 digit)
    public boolean isValidKTP() {
        return noKTP != null && noKTP.matches("\\d{16}");
    }

    // Validasi nomor HP (10-13 digit)
    public boolean isValidHP() {
        return noHP != null && noHP.matches("\\d{10,13}");
    }

    // Display info passenger
    public String displayInfo() {
        return "Passenger ID: " + passengerId + "\n" +
               "Nama: " + nama + "\n" +
               "No. KTP: " + noKTP + "\n" +
               "No. HP: " + noHP;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId='" + passengerId + '\'' +
                ", nama='" + nama + '\'' +
                ", noKTP='" + noKTP + '\'' +
                ", noHP='" + noHP + '\'' +
                '}';
    }
}