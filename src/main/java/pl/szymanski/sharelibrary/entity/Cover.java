package pl.szymanski.sharelibrary.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
@Table(name = "cover")
@NoArgsConstructor
public class Cover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private Book book;

    public Cover(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cover cover = (Cover) o;
        return Objects.equals(id, cover.id) &&
                Objects.equals(name, cover.name) &&
                Objects.equals(type, cover.type) &&
                Arrays.equals(data, cover.data) &&
                Objects.equals(book, cover.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, book);
    }
}
