package pl.szymanski.sharelibrary.models;


import lombok.Data;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String city;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String street;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String buildingNumber;

    @OneToOne(cascade = {MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinatesId")
    private Coordinates coordinates;
}
