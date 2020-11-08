package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Address;

import java.util.Optional;

public interface AddressJPARepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByCountryAndCityAndStreetAndBuildingNumber(String country, String city, String street, String buildingNumber);

}
