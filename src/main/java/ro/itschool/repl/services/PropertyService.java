package ro.itschool.repl.services;

import ro.itschool.repl.models.dtos.PropertyDTO;

import java.util.List;

public interface PropertyService {

    PropertyDTO createProperty(PropertyDTO propertyDTO);
    List<PropertyDTO> getProperties();
    PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO);
    void deleteProperty(Long id);
}
