package com.awesome.LittleBankerApplication.domain;

import com.awesome.LittleBankerApplication.models.CustomerModel;
import com.awesome.LittleBankerApplication.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerManagementService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerManagementService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public synchronized void registerNewCustomer(CustomerModel customerModel) {
        customerRepository.save(customerModel);
    }

    public synchronized List<CustomerModel> getAllRegisteredCustomers() {
        return customerRepository.findAll();
    }

    public synchronized void deleteCustomer(Integer id) {
        customerRepository.deleteById(Long.valueOf(id));
    }

    public synchronized void updateCustomerInformation(CustomerModel customerModelUpdateInfo) {
        Long customerId = Long.valueOf(customerModelUpdateInfo.getCustomerId());
        Optional<CustomerModel> customerById = customerRepository.findById(customerId);
        if (customerById.isPresent()) {
            logger.info("Customer Id " + customerId + " is being updated in db.");
            CustomerModel customerModelInDb = customerById.get();
            customerModelInDb.setName(customerModelUpdateInfo.getName());
            customerModelInDb.setSurname(customerModelUpdateInfo.getSurname());
            customerModelInDb.setSex(customerModelUpdateInfo.getSex());
            customerModelInDb.setNationality(customerModelUpdateInfo.getNationality());
            customerModelInDb.setDateOfBirth(customerModelUpdateInfo.getDateOfBirth());
            customerModelInDb.setCardNumber(customerModelUpdateInfo.getCardNumber());
            customerModelInDb.setDateOfCardExpiration(customerModelUpdateInfo.getDateOfCardExpiration());
            customerModelInDb.setDateOfCardIssue(customerModelUpdateInfo.getDateOfCardIssue());

            customerRepository.save(customerModelInDb);
        }
    }

}
