package pl.szymanski.sharelibrary.entity;

import lombok.Data;
import pl.szymanski.sharelibrary.enums.ExchangeStatus;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "exchange")
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, name = "exchange_status")
    private ExchangeStatus exchangeStatus;

    @Column(columnDefinition = "NUMBER")
    private Double deposit;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;

    @OneToMany(mappedBy = "exchange", fetch = FetchType.LAZY, cascade = ALL)
    private List<Requirement> requirements;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "for_book_id")
    private Book forBook;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "with_user_id")
    private User withUser;

    @Override
    public String toString() {
        return "Exchange{" +
                "id=" + id +
                ", exchangeStatus=" + exchangeStatus +
                ", deposit=" + deposit +
                ", book=" + book +
                ", user=" + user +
                ", coordinates=" + coordinates +
                ", forBook=" + forBook +
                ", withUser=" + withUser +
                '}';
    }
}
