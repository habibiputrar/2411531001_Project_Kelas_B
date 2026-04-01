
public class Magazine extends Book {
    private String genre;

    public Magazine(String title, String author, String genre) {
        super(title, author);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
