package ro.itschool.repl.services;

import ro.itschool.repl.models.dtos.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClientAndAddPropertyToFavorites(Long propertyId, ClientDTO clientDTO);
    List<ClientDTO> getClients();
    ClientDTO updateClient(Long id, ClientDTO clientDTO);
    void deleteClient(Long id);
}
