package com.netcracker.CoffeeShopApplication.customermanagement.service;

import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.customermanagement.repository.CustomerRepository;
import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer findById(String id) throws CustomException {
        return customerRepository.findById(id).orElseThrow(() -> {
            CustomException customException = new CustomException("Customer not found with Phone " + id);
            log.error(customException.getMessage(), customException);
            return customException;
        });
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

}
