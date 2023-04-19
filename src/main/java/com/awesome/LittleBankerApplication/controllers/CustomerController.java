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
 *   - registering new customer
 *   - deleting registered customer from db
 *   - updating registered customer information
 *   - listing all existing customers
 *        This functionality is available mainly for the testing,
 *        in real environment it will not be available on the public endpoint.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerManagementService customerManagementService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody CustomerModel customerModel) {
        logger.info("Attempt to register new customer initiated, customer name: " + customerModel.getName());

        customerManagementService.registerNewCustomer(customerModel);

        return ResponseEntity.ok().body("Customer " + customerModel.getName() + " was successfully registered. " +
                "You unique id is " + customerModel.getCustomerId());
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerModel customerModel) {
        logger.info("Attempt to update customer information initiated, customer name: " + customerModel.getName());

        customerManagementService.updateCustomerInformation(customerModel);

        return ResponseEntity.ok().body("Customer " + customerModel.getName() + " was successfully updated.");
    }

    @DeleteMapping(value = "/unregister")
    public ResponseEntity<?> deleteCustomer(@RequestBody Integer customerId) {
        logger.info("Attempt to unregister customer initiated, customer Id: " + customerId);

        customerManagementService.deleteCustomer(customerId);

        return ResponseEntity.ok().body("Customer Id " + customerId + " was successfully un-registered.");
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOverviewOfRegisteredCustomers() {
        logger.info("Attempt to get overview of all existing customers, this should be available only for admin.");

        List<CustomerModel> allRegisteredCustomers = customerManagementService.getAllRegisteredCustomers();

        return ResponseEntity.ok().body(allRegisteredCustomers != null && !allRegisteredCustomers.isEmpty() ?
                allRegisteredCustomers : "No registered customers were found.");
    }
    /**
     * Format the error message found by validator nicely, so it is readable by the user.
     */
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

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
