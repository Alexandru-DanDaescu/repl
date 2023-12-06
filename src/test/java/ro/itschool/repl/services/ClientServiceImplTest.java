package ro.itschool.repl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.itschool.repl.models.dtos.AddressDTO;
import ro.itschool.repl.models.dtos.ClientDTO;
import ro.itschool.repl.models.entities.Address;
import ro.itschool.repl.models.entities.Client;
import ro.itschool.repl.models.entities.Property;
import ro.itschool.repl.repositories.AddressRepository;
import ro.itschool.repl.repositories.ClientRepository;
import ro.itschool.repl.repositories.PropertyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @Test
    @DisplayName("Client created and added property to client successfully successfully")
    void addPropertyToClient(){

        Long propertyId = 1L;
        Property property = new Property();

        ClientDTO clientDTO = getClientDTO();

        Address address = new Address();
        address.setId(1L);
        address.setStreet("street");
        address.setCity("city");
        address.setPostalCode("4325234");
        address.setCounty("county");
        address.setCountry("country");

        Client client = new Client();
        client.setId(1L);
        client.setFullName("Test test");
        client.setAge((byte) 24);
        client.setGender("Male");
        client.setEmail("test@yahoo.com");
        client.setPhoneNumber("2354432");
        client.setAddress(address);

        Client savedClientEntity = new Client();
        savedClientEntity.setId(1L);
        savedClientEntity.setFullName("Test test");
        savedClientEntity.setAge((byte) 24);
        savedClientEntity.setGender("Male");
        savedClientEntity.setEmail("test@yahoo.com");
        savedClientEntity.setPhoneNumber("2354432");
        savedClientEntity.setAddress(address);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(objectMapper.convertValue(clientDTO, Client.class)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(objectMapper.convertValue(client, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO savedClientDTO = clientServiceImpl.createClientAndAddPropertyToFavorites(new Long[]{propertyId}, clientDTO);

        verify(clientRepository, times(2)).save(client);
        verify(propertyRepository, times(1)).save(property);
        assertEquals(clientDTO.getFullName(), savedClientDTO.getFullName());

    }


    @Test
    @DisplayName("Clients retrieved successfully")
    void getClients(){
        Client client1 = new Client();
        Client client2 = new Client();

        List<Client> allClients = Arrays.asList(client1,client2);

        when(clientRepository.findAll()).thenReturn(allClients);

        ClientDTO clientDTO1 = new ClientDTO();
        ClientDTO clientDTO2 = new ClientDTO();

        when(objectMapper.convertValue(client1,ClientDTO.class)).thenReturn(clientDTO1);
        when(objectMapper.convertValue(client2,ClientDTO.class)).thenReturn(clientDTO2);

        List<ClientDTO> clientDTOSInDB = clientServiceImpl.getClients();

        assertEquals(2, clientDTOSInDB.size());
        assertTrue(clientDTOSInDB.contains(clientDTO1));
        assertTrue(clientDTOSInDB.contains(clientDTO2));
    }

    @Test
    @DisplayName("Client deleted successfully")
    void deleteClient(){
        Long clientId = 1L;
        Client client = new Client();

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientServiceImpl.deleteClient(clientId);

        verify(clientRepository, times(1)).delete(client);
    }

    private static ClientDTO getClientDTO() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("street");
        addressDTO.setCity("city");
        addressDTO.setPostalCode("4325234");
        addressDTO.setCounty("county");
        addressDTO.setCountry("country");

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setFullName("Test test");
        clientDTO.setAge((byte) 24);
        clientDTO.setGender("Male");
        clientDTO.setEmail("test@yahoo.com");
        clientDTO.setPhoneNumber("2354432");
        clientDTO.setAddress(addressDTO);
        return clientDTO;
    }
}
