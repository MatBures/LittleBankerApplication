package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    Optional<AccountModel> findByIban(String iban);
}