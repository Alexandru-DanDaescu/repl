package ro.itschool.repl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.itschool.repl.exceptions.PropertyNotFoundException;
import ro.itschool.repl.exceptions.PropertyUpdateException;
import ro.itschool.repl.models.dtos.PropertyDTO;
import ro.itschool.repl.models.entities.Address;
import ro.itschool.repl.models.entities.Property;
import ro.itschool.repl.repositories.AddressRepository;
import ro.itschool.repl.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PropertyServiceImpl implements PropertyService{

    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, AddressRepository addressRepository, ObjectMapper objectMapper){
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
    }
    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {

        Property property = objectMapper.convertValue(propertyDTO, Property.class);

        if(property.getAddress() != null){
            Address savedAddress = addressRepository.save(property.getAddress());
            property.setAddress(savedAddress);
        }

        Property propertyResponseEntity = propertyRepository.save(property);
        return objectMapper.convertValue(propertyResponseEntity, PropertyDTO.class);
    }

    @Override
    public List<PropertyDTO> getProperties() {
       try {
           List<Property> propertyList = propertyRepository.findAll();
           List<PropertyDTO> propertyDTOList = new ArrayList<>();

           for(Property property : propertyList){
               propertyDTOList.add(convertToDTO(property));
           }

           if (propertyDTOList.isEmpty()){
               throw new PropertyNotFoundException("Properties cannot be found because they do not exist.");
           }
           return propertyDTOList;
       } catch (PropertyNotFoundException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
       }
    }

    @Override
    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO) {
        try {
            Property updatedProperty = propertyRepository.findById(id)
                    .map(property -> updatePropertyValues(property, propertyDTO))
                    .orElseThrow(() -> new PropertyNotFoundException("Property not found with id: " + id));
            Property savedProperty = propertyRepository.save(updatedProperty);
            return convertToDTO(savedProperty);
        }
        catch (PropertyNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (Exception e){
            throw new PropertyUpdateException("Failed to update property with id: " + id, e);
        }
    }

    @Override
    public void deleteProperty(Long id) {
        try {
            Property property = propertyRepository.findById(id)
                    .orElseThrow(() -> new PropertyNotFoundException("Property with id: " + id + " cannot be deleted because it is not found"));
            propertyRepository.delete(property);
        } catch (PropertyNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private Property updatePropertyValues(Property property, PropertyDTO propertyDTO){
        property.setSquareFootage(propertyDTO.getSquareFootage());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setSalesPrice(propertyDTO.getSalesPrice());
        property.setDaysOnTheMarket(propertyDTO.getDaysOnTheMarket());
        property.setYearBuilt(propertyDTO.getYearBuilt());
        return property;
    }

    private PropertyDTO convertToDTO(Property property){
        return  objectMapper.convertValue(property, PropertyDTO.class);
    }
}
