package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is a Spring Data JPA repository interface for AccountModel.
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    // This method signature declares a method for finding an AccountModel instance by its IBAN.
    Optional<AccountModel> findByIban(String iban);
}