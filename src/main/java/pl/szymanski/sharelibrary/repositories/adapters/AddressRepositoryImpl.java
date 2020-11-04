package pl.szymanski.sharelibrary.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.szymanski.sharelibrary.entity.Address;
import pl.szymanski.sharelibrary.repositories.jpa.AddressJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.AddressRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJPARepository addressJPARepository;

    @Override
    public Set<Address> findAll() {
        return new HashSet<>(addressJPARepository.findAll());
    }

    @Override
    public Optional<Address> getById(Long id) {
        return addressJPARepository.findById(id);
    }

    @Override
    public Address saveAndFlush(Address address) {
        return addressJPARepository.saveAndFlush(address);
    }
}
