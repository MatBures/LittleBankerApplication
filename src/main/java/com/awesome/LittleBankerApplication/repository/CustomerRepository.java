package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.AccountModel;
import com.awesome.LittleBankerApplication.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is a Spring Data JPA repository interface for CustomerModel.
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    // This method signature declares a method for finding a CustomerModel instance by its ID.
    Optional<CustomerModel> findById(Long id);
}
