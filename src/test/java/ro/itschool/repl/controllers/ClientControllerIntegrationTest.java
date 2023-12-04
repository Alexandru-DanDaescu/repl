package ro.itschool.repl.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.itschool.repl.models.dtos.AddressDTO;
import ro.itschool.repl.models.dtos.ClientDTO;
import ro.itschool.repl.services.ClientService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    @DisplayName("When creating client, return status code 201")
    void whenCreateClientTest() throws Exception{
        String clientJson = """
                  {
                     "fullName" : "someNameHere",
                     "age" : 22,
                     "gender" : "Male",
                     "email" : "email@gmail.com",
                     "phoneNumber" : "+2345456",
                     "address" : {
                         "street" : "testing",
                         "city" : "TEST",
                         "postalCode" : "456234",
                         "county" : "West",
                         "country" : "North"
                     },
                     "properties": [1]
                  }
                """;
        ClientDTO mockClientDTO = createClientDTO();

        given(clientService.createClientAndAddPropertyToFavorites(eq(1L),any(ClientDTO.class))).willReturn(mockClientDTO);
        mockMvc.perform(post("/clients/1/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName").value("someNameHere"))
                .andExpect(jsonPath("$.age").value("22"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("+2345456")).andReturn();

    }


    private AddressDTO addressDTO(){
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("testing");
        addressDTO.setCity("TEST");
        addressDTO.setPostalCode("456234");
        addressDTO.setCounty("West");
        addressDTO.setCountry("North");
        return addressDTO;
    }

    private ClientDTO createClientDTO(){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setFullName("someNameHere");
        clientDTO.setAge((byte) 22);
        clientDTO.setGender("Male");
        clientDTO.setEmail("email@gmail.com");
        clientDTO.setPhoneNumber("+2345456");
        clientDTO.setAddress(addressDTO());
        return clientDTO;
    }
}
