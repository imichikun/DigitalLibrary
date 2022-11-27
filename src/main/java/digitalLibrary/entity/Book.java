package digitalLibrary.entity;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private Reader reader;

    public Book(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public Reader getReader() {
        return reader;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}