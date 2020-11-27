package pl.szymanski.sharelibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.szymanski.sharelibrary.enums.BookStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_book")
@IdClass(UserBookPK.class)
public class UserBook {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "book_status")
    private BookStatus atOwner;

    @ManyToOne
    @JoinColumn(name = "atUserId")
    private User atUser;

}

