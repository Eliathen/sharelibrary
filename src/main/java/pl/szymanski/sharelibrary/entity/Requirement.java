package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "request")
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exchangeId")
    private Exchange exchange;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private LocalDateTime createTime;

    private boolean isActual;
}
