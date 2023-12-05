package ro.itschool.repl.services;

import ro.itschool.repl.enums.Utilities;
import ro.itschool.repl.models.dtos.ClientDTO;
import ro.itschool.repl.models.dtos.PropertyDTO;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ClientService {

    ClientDTO createClientAndAddPropertyToFavorites(Long propertyId, ClientDTO clientDTO);
    List<ClientDTO> getClients();

    List<PropertyDTO> sortClientProperties(Long clientId, Utilities utilitiesStatus, String propertyType, LocalDate yearBuilt);
    ClientDTO updateClient(Long id, ClientDTO clientDTO);
    void deleteClient(Long id);
}
