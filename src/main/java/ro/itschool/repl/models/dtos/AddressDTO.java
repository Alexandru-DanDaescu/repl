package ro.itschool.repl.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
@Data
@Validated
public class AddressDTO implements Serializable {

    private Long id;

    @NotEmpty(message = "Address street field cannot be empty")
    private String street;

    @NotEmpty(message = "Address city field cannot be empty")
    private String city;

    @NotEmpty(message = "Address postal code field cannot be empty")
    @Size(min = 1, message = "Address postal code has to be greater than 1")
    @Size(max = 12, message = "Address postal code has to be less than 12")
    private String postalCode;

    @NotEmpty(message = "Address county field cannot be empty")
    private String county;

    @NotEmpty(message = "Address country field cannot be empty")
    private String country;
}
