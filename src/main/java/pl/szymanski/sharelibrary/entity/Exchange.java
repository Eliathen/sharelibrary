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

    @Column(nullable = false)
    private ExchangeStatus exchangeStatus;

    @Column(columnDefinition = "NUMBER")
    private Double deposit;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "coordinatesId")
    private Coordinates coordinates;

    @OneToMany(mappedBy = "exchange", fetch = FetchType.LAZY, cascade = ALL)
    private List<Requirement> requirements;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "forBookId")
    private Book forBook;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "withUserId")
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
