package model;

public class CostumerBuilder {
    private String id;
    private String nama;
    private String email = "";
    private String alamat;
    private String hp;

    public CostumerBuilder() {
        // TODO Auto-generated constructor stub
    }

    public CostumerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CostumerBuilder setNama(String nama) {
        this.nama = nama;
        return this;
    }

    public CostumerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CostumerBuilder setAlamat(String alamat) {
        this.alamat = alamat;
        return this;
    }

    public CostumerBuilder setHp(String hp) {
        this.hp = hp;
        return this;
    }

    public Costumer build() {
        return new Costumer(id, nama, email, alamat, hp);
    }
}
