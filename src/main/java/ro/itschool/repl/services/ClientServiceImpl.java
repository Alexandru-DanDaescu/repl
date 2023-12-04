package ro.itschool.repl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.itschool.repl.exceptions.ClientNotFoundException;
import ro.itschool.repl.exceptions.ClientUpdateException;
import ro.itschool.repl.exceptions.PropertyNotFoundException;
import ro.itschool.repl.models.dtos.ClientDTO;
import ro.itschool.repl.models.entities.Address;
import ro.itschool.repl.models.entities.Client;
import ro.itschool.repl.models.entities.Property;
import ro.itschool.repl.repositories.AddressRepository;
import ro.itschool.repl.repositories.ClientRepository;
import ro.itschool.repl.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;

    public ClientServiceImpl(ClientRepository clientRepository, PropertyRepository propertyRepository, AddressRepository addressRepository, ObjectMapper objectMapper){
        this.clientRepository = clientRepository;
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ClientDTO createClientAndAddPropertyToFavorites(Long propertyId, ClientDTO clientDTO){
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found."));
        Client client = objectMapper.convertValue(clientDTO, Client.class);
        Client savedClientEntity = clientRepository.save(client);

        property.getClients().add(savedClientEntity);
        savedClientEntity.getProperties().add(property);
        propertyRepository.save(property);
        clientRepository.save(savedClientEntity);

        return objectMapper.convertValue(savedClientEntity, ClientDTO.class);
    }

    @Override
    public List<ClientDTO> getClients() {
        try {
            List<Client> clients = clientRepository.findAll();
            List<ClientDTO> clientDTOS = new ArrayList<>();

            for (Client client : clients) {
                clientDTOS.add(convertToDTO(client));
            }

            if (clientDTOS.isEmpty()) {
                throw new ClientNotFoundException("Clients cannot be found because they do not exist");
            }
            return clientDTOS;
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
       try {
           Client updatedClient = clientRepository.findById(id)
                   .map(client -> updateClientValues(client, clientDTO))
                   .orElseThrow(() -> new ClientNotFoundException("Client with id: " + id + " not found."));
           Client savedClient = clientRepository.save(updatedClient);
           return convertToDTO(savedClient);
       }
       catch (ClientNotFoundException e) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
       }
       catch (Exception e){
           throw new ClientUpdateException("Failed to update client with id:" + id, e);
       }

    }

    @Override
    public void deleteClient(Long id) {
        try {
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new ClientNotFoundException("Client with id:" + id + " cannot be deleted because they do not exist"));
            clientRepository.delete(client);
        } catch (ClientNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private ClientDTO convertToDTO(Client client){
        return objectMapper.convertValue(client, ClientDTO.class);
    }

    private Client updateClientValues(Client client, ClientDTO clientDTO){
        client.setFullName(clientDTO.getFullName());
        client.setAge(clientDTO.getAge());
        client.setGender(clientDTO.getGender());
        client.setEmail(clientDTO.getEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        return client;
    }
}
