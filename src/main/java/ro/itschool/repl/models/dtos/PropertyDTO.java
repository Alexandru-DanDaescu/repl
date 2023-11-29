package ro.itschool.repl.models.dtos;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ro.itschool.repl.enums.Utilities;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Validated
public class PropertyDTO implements Serializable {

    private Long id;

    @NotNull(message = "Property square footage field cannot be empty")
    @Min(value = 1, message = "Property square footage must be greater than 1")
    private double squareFootage;

    @NotEmpty(message = "Property type field cannot be empty")
    private String propertyType;

    @NotNull(message = "Property sales price field cannot be empty")
    @Min(value = 1, message = "Property sales price must be greater than 1")
    private double salesPrice;

    @NotNull(message = "Property days on the market field cannot be empty")
    @Min(value = 0, message = "Days on the market number should be greater than 0")
    @Max(value = 32767, message = "Days on the market number should be less than 32767")
    private short daysOnTheMarket;

    @NotNull(message = "Property year built field cannot be empty")
    private LocalDate yearBuilt;

    private Utilities utilitiesStatus;

    @NotNull(message = "Address cannot be empty")
    private AddressDTO address;
}
