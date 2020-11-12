package pl.szymanski.sharelibrary.commanddata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;


@Value
public class AddressCommandData {
    private String country;

    private String city;

    private String street;

    private String postalCode;

    private String buildingNumber;

    @JsonCreator
    public AddressCommandData(@JsonProperty(value = "country", required = true) String country,
                              @JsonProperty(value = "city", required = true) String city,
                              @JsonProperty(value = "postalCode", required = true) String postalCode,
                              @JsonProperty(value = "street", required = true) String street,
                              @JsonProperty(value = "buildingNumber", required = true) String buildingNumber) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.buildingNumber = buildingNumber;
    }
}
