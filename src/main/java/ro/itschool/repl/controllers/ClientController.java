package ro.itschool.repl.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.repl.models.dtos.ClientDTO;
import ro.itschool.repl.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping("/clients/{propertyId}/properties")
    public ResponseEntity<ClientDTO> createClientAndAddProperty(@PathVariable Long propertyId, @RequestBody @Valid ClientDTO clientDTO){
        ClientDTO savedClient = clientService.createClientAndAddPropertyToFavorites(propertyId, clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
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
