package pl.szymanski.sharelibrary.entity;

import lombok.Data;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Data
@Table(name = "exchange")
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private ExchangeStatus exchangeStatus;

    @Column(columnDefinition = "NUMBER")
    private Double deposit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {PERSIST, MERGE})
    @JoinColumn(name = "coordinatesId")
    private Coordinates coordinates;

    @OneToMany(mappedBy = "exchange", fetch = FetchType.LAZY)
    private Set<Request> requests;

}
