package pl.szymanski.sharelibrary.models;

import lombok.Data;
import pl.szymanski.sharelibrary.enums.BookCondition;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private Boolean isFinished;

    @Column(columnDefinition = "NUMBER")
    private Double deposit;

    private BookCondition bookCondition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToMany(mappedBy = "exchange")
    private Set<Request> requests;

}
