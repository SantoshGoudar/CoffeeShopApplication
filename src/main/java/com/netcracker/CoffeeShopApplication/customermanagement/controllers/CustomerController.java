package com.netcracker.CoffeeShopApplication.customermanagement.controllers;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.customermanagement.models.Customer;
import com.netcracker.CoffeeShopApplication.customermanagement.repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffeeshop")
@Api(value = "CustomerMangement API", produces = "application/json", description = "All Customer Management related APIS of CoffeeShop Application"
)
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CustomerRepository repository;

    @GetMapping("/customers")
    @ApiOperation(value = "Retrieves all the customers of CoffeeShop", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customers  Retrieved", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Customers not found"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })

    public List<Customer> getAllCustomers() {
        logger.info(" list all Customers");
        return repository.findAll();
    }

    @PostMapping("/customers")
    @ApiOperation(value = "Add new Customer to the CoffeeShop", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer added to DB", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not add Customer"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })

    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        logger.info(" add a new customer with ID " + customer.getPhone());
        return repository.save(customer);
    }

    @GetMapping("/customers/{id}")
    @ApiOperation(value = "Get Customer with Phone number ", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get Customer  ", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Customer"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public Customer getOne(@PathVariable String id) throws Exception {
        logger.info(" find one customer by ID " + id);

        Optional<Customer> byId = repository.findById(id);
        if (!byId.isPresent()) {
            logger.error("No csutomer with given phone number present " + id);
            throw new CustomException("No customer present with phone number " + id);
        }
        return byId.get();
    }

    @PutMapping("/customers")
    public Customer update(@RequestBody @Valid Customer customer) {
        logger.info("update customer");
        return repository.save(customer);

    }

    @DeleteMapping("/customers/{id}")
    @ApiOperation(value = "Delete Customer with Phone number ", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted Customer successfully", response = Customer.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Could not get Customer"),
            @ApiResponse(code = 403, message = "User not Authorized")
    })
    public String delete(@PathVariable String id) {
        logger.info("delete customer with ID " + id);
        repository.deleteById(id);
        return "Deleted succesfully";
    }
}
