package com.awesome.LittleBankerApplication.domain;

import com.awesome.LittleBankerApplication.models.CustomerModel;
import com.awesome.LittleBankerApplication.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Transaction Management Service is for managing customer data. It provides methods for registering new customers,
 * retrieving a list of all registered customers, deleting customers with a specified ID,
 * and updating the information of a customer in the repository. The service uses a logger
 * to log important information and events, and it relies on a CustomerRepository for
 * managing customer data.
 */
@Service
public class CustomerManagementService {

    // A logger to log important information and events in the service.
    private static final Logger logger = LoggerFactory.getLogger(CustomerManagementService.class);

    // A repository for managing customer data.
    private CustomerRepository customerRepository;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    // Register a new customer by saving their details to the repository.
    public synchronized void registerNewCustomer(CustomerModel customerModel) {
        customerRepository.save(customerModel);
    }

    // Retrieves a list of all registered customers from the repository.
    public synchronized List<CustomerModel> getAllRegisteredCustomers() {
        return customerRepository.findAll();
    }

    // Deletes a customer with the specified ID from the repository.
    public synchronized boolean deleteCustomer(Long id) {

        // Check if the customer with the given id exists in the repository.
        Optional<CustomerModel> customer = customerRepository.findById(id);

        // If the customer exists, delete them from the repository and return true.
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }

        // If the customer doesn't exist, return false.
        return false;
    }

    // Updates the information of a customer in the repository.
    public synchronized void updateCustomerInformation(CustomerModel customerModelUpdateInfo) {
        Long customerId = Long.valueOf(customerModelUpdateInfo.getCustomerId());
        Optional<CustomerModel> customerById = customerRepository.findById(customerId);

        if (!customerById.isPresent()) {
            throw new NoSuchElementException("Customer with id " + customerId + " does not exist");
        }

        logger.info("Customer Id " + customerId + " is being updated in db.");
        CustomerModel customerModelInDb = customerById.get();
        customerModelInDb.setName(customerModelUpdateInfo.getName());
        customerModelInDb.setSurname(customerModelUpdateInfo.getSurname());
        customerModelInDb.setSex(customerModelUpdateInfo.getSex());
        customerModelInDb.setNationality(customerModelUpdateInfo.getNationality());
        customerModelInDb.setDateOfBirth(customerModelUpdateInfo.getDateOfBirth());
        customerModelInDb.setCardNumber(customerModelUpdateInfo.getCardNumber());
        customerModelInDb.setDateOfCardIssue(customerModelUpdateInfo.getDateOfCardIssue());
        customerModelInDb.setDateOfCardExpiration(customerModelUpdateInfo.getDateOfCardExpiration());

        customerRepository.save(customerModelInDb);
    }

}
