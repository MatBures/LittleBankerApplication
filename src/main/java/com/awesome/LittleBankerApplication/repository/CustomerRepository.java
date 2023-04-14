package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
}
