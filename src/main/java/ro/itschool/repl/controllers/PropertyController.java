package ro.itschool.repl.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.repl.models.dtos.PropertyDTO;
import ro.itschool.repl.services.PropertyService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @PostMapping("/properties")
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody @Valid PropertyDTO propertyDTO){
        return ResponseEntity.ok(propertyService.createProperty(propertyDTO));
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getProperties(){
        List<PropertyDTO> propertyDTOList = propertyService.getProperties();
        if(propertyDTOList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(propertyDTOList);
        }
    }

    @PutMapping("/properties/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO){
        PropertyDTO updatePropertyDTO = propertyService.updateProperty(id, propertyDTO);
        return ResponseEntity.ok().body(updatePropertyDTO);
    }

    @DeleteMapping("/properties/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id){
        propertyService.deleteProperty(id);
        return ResponseEntity.ok("Property with id: " + id + " got deleted successfully.");
    }
}
