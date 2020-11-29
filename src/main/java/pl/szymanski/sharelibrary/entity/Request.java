package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exchangeId")
    private Exchange exchange;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private boolean isActual;
}
