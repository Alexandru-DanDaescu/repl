package ro.itschool.repl.models.dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ro.itschool.repl.models.entities.Address;

import java.io.Serializable;
import java.util.Set;

@Data
@Validated
public class ClientDTO implements Serializable {

    private Long id;

    @NotEmpty(message = "Client full name field cannot be empty")
    private String fullName;

    @NotNull(message = "Client age field cannot be empty")
    @Min(value = 18, message = "Client age cannot be less than 18")
    @Max(value = 100, message = "Client age cannot be greater than 100")
    private byte age;

    @NotEmpty(message = "Client gender field cannot be empty")
    private String gender;

    @NotEmpty(message = "Client email field cannot be empty")
    private String email;

    @NotEmpty(message = "Client phone number field cannot be empty")
    private String phoneNumber;

    @NotNull(message = "Address cannot be empty")
    private AddressDTO address;

}
