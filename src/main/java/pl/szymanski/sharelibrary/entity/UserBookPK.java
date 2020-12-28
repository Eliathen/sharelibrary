package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class UserBookPK implements Serializable {
    private Long user;
    private Long book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBookPK that = (UserBookPK) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }
}
