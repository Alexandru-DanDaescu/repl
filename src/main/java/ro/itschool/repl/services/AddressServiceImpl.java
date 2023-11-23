package ro.itschool.repl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.repl.repositories.AddressRepository;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    private final ObjectMapper objectMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ObjectMapper objectMapper){
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
    }
}
