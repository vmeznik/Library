package library.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "library2", schema = "public")
public class Book implements Serializable {

    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;
    @Column(name = "status")
    private String status;
    @Column(name = "authorsfirstname")
    private String authorsFirstName;
    @Column(name = "authorslastname")
    private String authorsLastName;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //book has only status : open/close !!

    public Book(int id, String title, String genre, String status, String authorsFirstName, String authorsLastName) {
        this.title = title;
        this.genre = genre;
        this.status = status;
        this.authorsFirstName = authorsFirstName;
        this.authorsLastName = authorsLastName;
        this.id = id;
    }

    public Book(){}

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatus() {
        return status;
    }

    public String getAuthorsFirstName() {
        return authorsFirstName;
    }

    public String getAuthorsLastName() {
        return authorsLastName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAuthorsFirstName(String authorsFirstName) {
        this.authorsFirstName = authorsFirstName;
    }

    public void setAuthorsLastName(String authorsLastName) {
        this.authorsLastName = authorsLastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " " +
                title + " by " +
                authorsFirstName + " " +
                authorsLastName + " - " +
                status + " ("
                + genre + ") ";
    }
}
