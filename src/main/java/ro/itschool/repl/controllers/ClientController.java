package ro.itschool.repl.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.repl.enums.Utilities;
import ro.itschool.repl.models.dtos.ClientDTO;
import ro.itschool.repl.models.dtos.PropertyDTO;
import ro.itschool.repl.services.ClientService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping("/clients/{propertyIds}/properties")
    public ResponseEntity<ClientDTO> createClientAndAddProperty(@PathVariable Long[] propertyIds, @RequestBody @Valid ClientDTO clientDTO){
        ClientDTO savedClient = clientService.createClientAndAddPropertyToFavorites(propertyIds, clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping("/clients-properties/{clientId}")
    public ResponseEntity<List<PropertyDTO>> sortClientProperties(@PathVariable Long clientId,
                                                                  @RequestParam(required = false) Utilities utilitiesStatus,
                                                                  @RequestParam(required = false) String propertyType,
                                                                  @RequestParam(required = false) LocalDate yearBuilt) {
        return ResponseEntity.ok(clientService.sortClientProperties(clientId,utilitiesStatus,propertyType,yearBuilt));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClients(){
        List<ClientDTO> clientDTOList = clientService.getClients();
        if(clientDTOList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(clientDTOList);
        }
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id,@RequestBody ClientDTO clientDTO){
        ClientDTO updateClientDTO = clientService.updateClient(id,clientDTO);
        return ResponseEntity.ok().body(updateClientDTO);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client with id: " + id + "successfully deleted.");
    }
}
