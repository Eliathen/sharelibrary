package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Address;

public interface AddressJPARepository extends JpaRepository<Address, Long> {

}
