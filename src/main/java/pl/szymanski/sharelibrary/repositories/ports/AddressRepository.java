package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Address;

import java.util.Optional;
import java.util.Set;

public interface AddressRepository {

    Set<Address> findAll();

    Optional<Address> getById(Long id);

    Address saveAndFlush(Address address);
}
