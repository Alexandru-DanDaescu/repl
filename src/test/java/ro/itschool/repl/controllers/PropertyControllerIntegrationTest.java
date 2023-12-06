package ro.itschool.repl.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.itschool.repl.enums.Utilities;
import ro.itschool.repl.models.dtos.AddressDTO;
import ro.itschool.repl.models.dtos.PropertyDTO;
import ro.itschool.repl.services.PropertyService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PropertyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;


    @Test
    @DisplayName("When creating property, return status code 201")
    void whenCreatePropertyTest() throws Exception {
        String propertyJson = """
                  {
                     "squareFootage" : 12.34,
                     "propertyType" : "again",
                     "salesPrice" : 435.345,
                     "daysOnTheMarket" : 23,
                     "yearBuilt" : "2015-12-01",
                     "utilitiesStatus" : "BAD",
                     "address" : {
                         "street" : "testing",
                         "city" : "TEST",
                         "postalCode" : "456234",
                         "county" : "West",
                         "country" : "North"
                     }
                  }
                """;
        PropertyDTO mockPropertyDTO = new PropertyDTO();
        mockPropertyDTO.setSquareFootage(12.34);
        mockPropertyDTO.setPropertyType("again");
        mockPropertyDTO.setSalesPrice(435.345);
        mockPropertyDTO.setDaysOnTheMarket((short) 23);
        mockPropertyDTO.setYearBuilt(LocalDate.parse("2015-12-01"));
        mockPropertyDTO.setUtilitiesStatus(Utilities.BAD);


        given(propertyService.createProperty(any(PropertyDTO.class))).willReturn(mockPropertyDTO);
        mockMvc.perform(post("/api/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(propertyJson))
                .andExpect(status().isCreated()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.squareFootage").value("12.34"))
                .andExpect(jsonPath("$.propertyType").value("again"))
                .andExpect(jsonPath("$.salesPrice").value("435.345"))
                .andExpect(jsonPath("$.daysOnTheMarket").value("23"))
                .andExpect(jsonPath("$.yearBuilt").value("2015-12-01"))
                .andExpect(jsonPath("$.utilitiesStatus").value("BAD")).andReturn();
    }

    @Test
    @DisplayName("Get property list and return status code 200")
    void whenGetPropertyList() throws Exception {
        PropertyDTO propertyDTO = createPropertyDTO();

        List<PropertyDTO> propertyDTOList = List.of(propertyDTO);
        given(propertyService.getProperties()).willReturn(propertyDTOList);

        mockMvc.perform(get("/api/properties")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(propertyDTO.getId()))
                .andExpect(jsonPath("$[0].squareFootage").value(propertyDTO.getSquareFootage()))
                .andExpect(jsonPath("$[0].propertyType").value(propertyDTO.getPropertyType()))
                .andExpect(jsonPath("$[0].salesPrice").value(propertyDTO.getSalesPrice()))
                .andExpect(jsonPath("$[0].daysOnTheMarket", Matchers.equalTo(Integer.valueOf(propertyDTO.getDaysOnTheMarket()))))
                .andExpect(jsonPath("$[0].yearBuilt").value(propertyDTO.getYearBuilt().toString()))
                .andExpect(jsonPath("$[0].utilitiesStatus").value(propertyDTO.getUtilitiesStatus().toString()))
                .andExpect(jsonPath("$[0].address.street").value(propertyDTO.getAddress().getStreet()))
                .andExpect(jsonPath("$[0].address.city").value(propertyDTO.getAddress().getCity()))
                .andExpect(jsonPath("$[0].address.postalCode").value(propertyDTO.getAddress().getPostalCode()))
                .andExpect(jsonPath("$[0].address.county").value(propertyDTO.getAddress().getCounty()))
                .andExpect(jsonPath("$[0].address.country").value(propertyDTO.getAddress().getCountry()));

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

    private PropertyDTO createPropertyDTO(){
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(1L);
        propertyDTO.setSquareFootage(12.34);
        propertyDTO.setPropertyType("again");
        propertyDTO.setSalesPrice(435.345);
        propertyDTO.setDaysOnTheMarket((short) 23);
        propertyDTO.setYearBuilt(LocalDate.parse("2015-12-01"));
        propertyDTO.setUtilitiesStatus(Utilities.BAD);
        propertyDTO.setAddress(addressDTO());
        return propertyDTO;
    }
}
