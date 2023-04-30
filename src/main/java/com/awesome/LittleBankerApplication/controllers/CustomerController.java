package com.awesome.LittleBankerApplication.controllers;

import com.awesome.LittleBankerApplication.domain.CustomerManagementService;
import com.awesome.LittleBankerApplication.models.CustomerModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Customer controller
 * Responsible for listing to REST requests regarding:
 *   registering new customer
 *   deleting registered customer from db
 *   updating registered customer information
 *   listing all existing customers
 *      This functionality is available mainly for the testing,
 *      in real environment it will not be available on the public endpoint.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    // A logger to log important information and events in the service.
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerManagementService customerManagementService;

    // Endpoint for creating a new customer.
    @PostMapping(value = "/register")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody CustomerModel customerModel) {

        // Log that a new customer registration is being attempted.
        logger.info("Attempt to register new customer initiated, customer name: " + customerModel.getName());

        // Call the customer management service to create the new customer.
        customerManagementService.registerNewCustomer(customerModel);

        // Return a success response with the newly created customer's ID.
        return ResponseEntity.ok().body("Customer " + customerModel.getName() + " was successfully registered. " +
                "Your unique id is " + customerModel.getCustomerId());
    }


    //Endpoint for updating an existing customer.
    @PostMapping(value = "/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerModel customerModel) {

        // Log that an existing customer update is being attempted.
        logger.info("Attempt to update customer information initiated, customer name: " + customerModel.getName());

        // Call the customer management service to update the customer.
        customerManagementService.updateCustomerInformation(customerModel);

        // Return a success response.
        return ResponseEntity.ok().body("Customer " + customerModel.getName() + " was successfully updated.");
    }

    @DeleteMapping(value = "/unregister/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        logger.info("Attempt to unregister customer initiated, customer Id: " + customerId);
        boolean success = customerManagementService.deleteCustomer(customerId);

        if (success) {
            return ResponseEntity.ok().body("Customer Id " + customerId + " was successfully un-registered.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Id " + customerId + " not found.");
        }
    }

    // Endpoint for retrieving an overview of all registered customers.
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOverviewOfRegisteredCustomers() {

        // Log that an overview of all existing customers is being requested.
        logger.info("Attempt to get overview of all existing customers, this should be available only for admin.");

        // Call the customer management service to retrieve all registered customers.
        List<CustomerModel> allRegisteredCustomers = customerManagementService.getAllRegisteredCustomers();

        // If customers were found, return them in the response body. Otherwise, return an error message.
        return ResponseEntity.ok().body(allRegisteredCustomers != null && !allRegisteredCustomers.isEmpty() ?
                allRegisteredCustomers : "No registered customers were found.");
    }
    /**
     * Format the error message found by validator nicely, so it is readable by the user.
     */
    // Exception handler for validating request body
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    // Helper method for creating error response
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
