package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@Table(name = "coordinates")
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;

    @OneToOne(fetch = FetchType.EAGER, cascade = {PERSIST, MERGE, REFRESH}, mappedBy = "coordinates" )
    private Address address;
}
