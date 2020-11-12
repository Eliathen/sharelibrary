package pl.szymanski.sharelibrary.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String country;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String city;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String street;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String postalCode;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String buildingNumber;

    @OneToOne(cascade = {MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinatesId")
    private Coordinates coordinates;
}
