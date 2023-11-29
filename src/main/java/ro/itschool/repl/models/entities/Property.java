package ro.itschool.repl.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.itschool.repl.enums.Utilities;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "square_footage")
    private double squareFootage;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "sales_price")
    private double salesPrice;

    @Column(name = "days_on_the_market")
    private short daysOnTheMarket;

    @Column(name = "year_built")
    private LocalDate yearBuilt;

    @Enumerated(EnumType.STRING)
    @Column(name = "utilities_status")
    private Utilities utilitiesStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;
}
