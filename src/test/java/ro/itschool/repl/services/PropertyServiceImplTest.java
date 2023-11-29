package ro.itschool.repl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ro.itschool.repl.enums.Utilities;
import ro.itschool.repl.exceptions.PropertyNotFoundException;
import ro.itschool.repl.models.dtos.AddressDTO;
import ro.itschool.repl.models.dtos.PropertyDTO;
import ro.itschool.repl.models.entities.Address;
import ro.itschool.repl.models.entities.Property;
import ro.itschool.repl.repositories.AddressRepository;
import ro.itschool.repl.repositories.PropertyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private PropertyServiceImpl propertyServiceImpl;

    @Test
    @DisplayName("Property created successfully")
    void createProperty(){
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("street");
        addressDTO.setCity("city");
        addressDTO.setPostalCode("4325234");
        addressDTO.setCounty("county");
        addressDTO.setCountry("country");

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(1L);
        propertyDTO.setSquareFootage(23.45);
        propertyDTO.setPropertyType("typeHere");
        propertyDTO.setSalesPrice(234.423);
        propertyDTO.setDaysOnTheMarket((short) 34);
        propertyDTO.setYearBuilt(LocalDate.parse("2020-12-03"));
        propertyDTO.setUtilitiesStatus(Utilities.GOOD);
        propertyDTO.setAddress(addressDTO);

        Address address = new Address();
        address.setId(1L);
        address.setStreet("street");
        address.setCity("city");
        address.setPostalCode("4325234");
        address.setCounty("county");
        address.setCountry("country");

        Property property = new Property();
        property.setId(1L);
        property.setSquareFootage(23.45);
        property.setPropertyType("typeHere");
        property.setSalesPrice(234.423);
        property.setDaysOnTheMarket((short) 34);
        property.setYearBuilt(LocalDate.parse("2020-12-03"));
        property.setUtilitiesStatus(Utilities.GOOD);
        property.setAddress(address);

        Property savedPropertyEntity = new Property();
        savedPropertyEntity.setId(1L);
        savedPropertyEntity.setSquareFootage(23.45);
        savedPropertyEntity.setPropertyType("typeHere");
        savedPropertyEntity.setSalesPrice(234.423);
        savedPropertyEntity.setDaysOnTheMarket((short) 34);
        savedPropertyEntity.setYearBuilt(LocalDate.parse("2020-12-03"));
        savedPropertyEntity.setUtilitiesStatus(Utilities.GOOD);
        savedPropertyEntity.setAddress(address);


        when(objectMapper.convertValue(propertyDTO, Property.class)).thenReturn(property);
        when(propertyRepository.save(property)).thenReturn(savedPropertyEntity);
        when(objectMapper.convertValue(savedPropertyEntity, PropertyDTO.class)).thenReturn(propertyDTO);

        PropertyDTO savedPropertyDTO = propertyServiceImpl.createProperty(propertyDTO);

        assertEquals(propertyDTO, savedPropertyDTO);
    }

    @Test
    @DisplayName("Properties retrieved successfully")
    void getProperties(){
        Property property1 = new Property();
        Property property2 = new Property();
        Property property3 = new Property();

        List<Property> allProperties = Arrays.asList(property1,property2,property3);

        when(propertyRepository.findAll()).thenReturn(allProperties);

        PropertyDTO propertyDTO1 = new PropertyDTO();
        PropertyDTO propertyDTO2 = new PropertyDTO();
        PropertyDTO propertyDTO3 = new PropertyDTO();

        when(objectMapper.convertValue(property1,PropertyDTO.class)).thenReturn(propertyDTO1);
        when(objectMapper.convertValue(property2,PropertyDTO.class)).thenReturn(propertyDTO2);
        when(objectMapper.convertValue(property3,PropertyDTO.class)).thenReturn(propertyDTO3);

        List<PropertyDTO> propertyDTOSInDB = propertyServiceImpl.getProperties();

        assertEquals(3, propertyDTOSInDB.size());
        assertTrue(propertyDTOSInDB.contains(propertyDTO1));
        assertTrue(propertyDTOSInDB.contains(propertyDTO2));
        assertTrue(propertyDTOSInDB.contains(propertyDTO3));
    }

    @Test
    @DisplayName("Property successfully updated")
    void updateProperty(){ //Test result is incorrect because it can't find the property id.

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("street");
        addressDTO.setCity("city");
        addressDTO.setPostalCode("4325234");
        addressDTO.setCounty("county");
        addressDTO.setCountry("country");

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(1L);
        propertyDTO.setSquareFootage(23.45);
        propertyDTO.setPropertyType("typeHere");
        propertyDTO.setSalesPrice(234.423);
        propertyDTO.setDaysOnTheMarket((short) 34);
        propertyDTO.setYearBuilt(LocalDate.parse("2020-12-03"));
        propertyDTO.setUtilitiesStatus(Utilities.GOOD);
        propertyDTO.setAddress(addressDTO);

        PropertyDTO updatedPropertyDTO = new PropertyDTO();
        updatedPropertyDTO.setId(propertyDTO.getId());
        updatedPropertyDTO.setSquareFootage(231.45);
        updatedPropertyDTO.setPropertyType("new");
        updatedPropertyDTO.setSalesPrice(23413.683);
        updatedPropertyDTO.setDaysOnTheMarket((short) 38);
        updatedPropertyDTO.setYearBuilt(LocalDate.parse("2010-12-03"));

        when(propertyServiceImpl.updateProperty(propertyDTO.getId(),propertyDTO)).thenReturn(updatedPropertyDTO);

        assertNotEquals(propertyDTO,updatedPropertyDTO);
    }

    @Test
    @DisplayName("Successfully deleted property")
    void deletePropertyById(){
        Long propertyId = 1L;
        Property property = new Property();
        property.setId(propertyId);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        propertyServiceImpl.deleteProperty(propertyId);

        verify(propertyRepository, times(1)).delete(property);
    }


}
