package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
}
