package com.awesome.LittleBankerApplication.servicesTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.awesome.LittleBankerApplication.domain.CustomerManagementService;
import com.awesome.LittleBankerApplication.models.CustomerModel;
import com.awesome.LittleBankerApplication.repository.CustomerRepository;

/**
 * Customer Management Service Test contains JUnit test cases for the CustomerManagementService class.
 */
@SpringBootTest
public class CustomerManagementServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerManagementService customerManagementService;

    // Deletes all customers from the repository before each test case.
    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    // Tests the registration of a new customer.
    @Test
    public void registerNewCustomerTest() {

        // Creates new customer
        CustomerModel customer = new CustomerModel("Jan", "Novak", "Male", "Czech Republic", LocalDate.of(1995, 7, 10), "123456789", LocalDate.of(2021, 1, 1), LocalDate.of(2031, 12, 31));

        // Register the customer
        customerManagementService.registerNewCustomer(customer);

        // Get all registered customers and check if the new customer is among them
        List<CustomerModel> allCustomers = customerManagementService.getAllRegisteredCustomers();
        assertEquals(1, allCustomers.size());
        CustomerModel savedCustomer = allCustomers.get(0);
        assertEquals(customer.getName(), savedCustomer.getName());
        assertEquals(customer.getSurname(), savedCustomer.getSurname());
        assertEquals(customer.getSex(), savedCustomer.getSex());
        assertEquals(customer.getNationality(), savedCustomer.getNationality());
        assertEquals(customer.getDateOfBirth(), savedCustomer.getDateOfBirth());
        assertEquals(customer.getCardNumber(), savedCustomer.getCardNumber());
        assertEquals(customer.getDateOfCardIssue(), savedCustomer.getDateOfCardIssue());
        assertEquals(customer.getDateOfCardExpiration(), savedCustomer.getDateOfCardExpiration());
    }

    // Tests the retrieval of all registered customers.
    @Test
    public void getAllRegisteredCustomersTest() {

        // Create a list of customers
        List<CustomerModel> customers = new ArrayList<>();
        customers.add(new CustomerModel("Jan", "Novak", "Male", "Czech Republic", LocalDate.of(1995, 7, 10), "123456789", LocalDate.of(2021, 1, 1), LocalDate.of(2031, 12, 31)));
        customers.add(new CustomerModel("Petr", "Svoboda", "Male", "Czech Republic", LocalDate.of(1985, 5, 23), "987654321", LocalDate.of(2021, 1, 1), LocalDate.of(2031, 12, 31)));
        customers.add(new CustomerModel("Josef", "Kost", "Male", "Czech Republic", LocalDate.of(1998, 9, 1), "456789123", LocalDate.of(2021, 1, 1), LocalDate.of(2031, 12, 31)));

        // Register each customer
        customers.forEach(customerManagementService::registerNewCustomer);

        // Retrieve all registered customers and check if their count matches the count of the original list of customers
        List<CustomerModel> registeredCustomers = customerManagementService.getAllRegisteredCustomers();

        assertEquals(customers.size(), registeredCustomers.size());
    }

    // Tests the deletion of a customer.
    @Test
    public void deleteCustomerTest() {

        // Create a new customer and register them
        CustomerModel customer1 = new CustomerModel("Jan", "Novak", "Male", "Czech Republic", LocalDate.of(1995, 7, 10), "123456789", LocalDate.of(2021, 1, 1), LocalDate.of(2031, 12, 31));
        customerManagementService.registerNewCustomer(customer1);

        // Get the ID of the registered customer
        Long customerId = customer1.getCustomerId();

        // Delete the registered customer by their ID
        customerManagementService.deleteCustomer(customerId);

        // Check that the customer was deleted from the repository
        assertNull(customerRepository.findById(customerId).orElse(null));
    }

    @Test
    public void updateCustomerInformationTest() {

        // Create a new customer and register it
        CustomerModel customer = new CustomerModel();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setSex("Male");
        customer.setNationality("American");
        customer.setDateOfBirth(LocalDate.of(1980, 1, 1));
        customer.setCardNumber("1234-5678-9012-3456");
        customer.setDateOfCardIssue(LocalDate.of(2020, 1, 1));
        customer.setDateOfCardExpiration(LocalDate.of(2025, 1, 1));
        customerManagementService.registerNewCustomer(customer);

        // Get the customer from the repository
        Optional<CustomerModel> customerFromDb = customerRepository.findById(customer.getCustomerId());

        // Update the customer's information
        if (customerFromDb.isPresent()) {
            CustomerModel customerToUpdate = customerFromDb.get();
            customerToUpdate.setName("Jane");
            customerToUpdate.setSurname("Doe");
            customerManagementService.updateCustomerInformation(customerToUpdate);

            // Check that the customer's information was updated correctly
            Optional<CustomerModel> updatedCustomerFromDb = customerRepository.findById(customer.getCustomerId());
            if (updatedCustomerFromDb.isPresent()) {
                assertEquals("Jane", updatedCustomerFromDb.get().getName());
                assertEquals("Doe", updatedCustomerFromDb.get().getSurname());
            } else {
                fail("Could not find updated customer in repository.");
            }
        } else {
            fail("Could not find customer in repository.");
        }
    }
}