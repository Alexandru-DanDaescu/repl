package ro.itschool.repl.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
@Data
@Validated
public class AddressDTO implements Serializable {

    private Long id;

    @NotEmpty(message = "Address street field cannot be empty")
    private String address;

    @NotEmpty(message = "Address city field cannot be empty")
    private String city;

    @NotEmpty(message = "Address postal code field cannot be empty")
    private String postalCode;

    @NotEmpty(message = "Address county field cannot be empty")
    private String county;

    @NotEmpty(message = "Address country field cannot be empty")
    private String country;
}
