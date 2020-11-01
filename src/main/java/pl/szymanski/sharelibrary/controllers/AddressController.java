package pl.szymanski.sharelibrary.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szymanski.sharelibrary.services.AddressService;
import pl.szymanski.sharelibrary.services.servicedata.AddressData;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public Set<AddressData> getList() {
        return addressService.getAddresses();
    }

}
