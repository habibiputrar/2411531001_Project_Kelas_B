
public class User {
    private String name;

    public User() {
        this.name = "Pengguna Umum";
    }

    public User(String name) {
        this.name = name;
    }
    	// Menampilkan detail buku menggunakan polymorphism
    public void viewBookDetails(Book book) {
        System.out.println("Judul: " + book.getTitle());
        System.out.println("Penulis: " + book.getAuthor());
        System.out.println("Tersedia: " + (book.isAvailable() ? "Ya" : "Tidak"));

        // Polymorphism: cek tipe buku
        if (book instanceof Novel) {
            Novel novel = (Novel) book;
            System.out.println("Genre: " + novel.getGenre());
        } else if (book instanceof Magazine) {
            Magazine magazine = (Magazine) book;
            System.out.println("Kategori: " + magazine.getGenre());
        } else if (book instanceof Textbook) {
            Textbook textbook = (Textbook) book;
            System.out.println("Bidang Studi: " + textbook.getGenre());
        }
    }
    // Meminjam Buku
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.borrowBook();
            System.out.println("Buku \"" + book.getTitle() + "\" berhasil dipinjam oleh " + this.name + ".");
        } else {
            System.out.println("Maaf " + this.name + ", buku \"" + book.getTitle() + "\" sedang tidak tersedia.");
        }
    }
    // Mengembalikan Buku
    public void returnBook(Book book) {
        if (!book.isAvailable()) {
            book.returnBook();
            System.out.println("Buku \"" + book.getTitle() + "\" berhasil dikembalikan.");
        } else {
            System.out.println("Buku \"" + book.getTitle() + "\" sudah tersedia.");
        }
    }
}
