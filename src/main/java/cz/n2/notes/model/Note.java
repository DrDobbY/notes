package cz.n2.notes.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Note {
    @Id
    @SequenceGenerator(
            name = "note_sequence",
            sequenceName = "note_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "note_sequence"
    )
    private Long id;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private String heading;
    private String body;

    public Note() {
    }

    public Note(Long id, LocalDate creationDate, LocalDate modificationDate, String heading, String body) {
        this.id = id;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.heading = heading;
        this.body = body;
    }

    public Note(LocalDate creationDate, LocalDate modificationDate, String heading, String body) {
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.heading = heading;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", heading='" + heading + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
