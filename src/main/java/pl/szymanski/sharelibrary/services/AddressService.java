package pl.szymanski.sharelibrary.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szymanski.sharelibrary.repositories.ports.AddressRepository;
import pl.szymanski.sharelibrary.services.servicedata.AddressData;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public Set<AddressData> getAddresses() {
        return addressRepository
                .findAll()
                .stream().map(AddressData::of)
                .collect(Collectors.toSet());
    }

}
