package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
}
