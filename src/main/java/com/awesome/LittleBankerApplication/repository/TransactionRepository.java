package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    List<TransactionModel> findByAmountTransferred(Double amountTransferred);
    List<TransactionModel> findBySourceIbanOrTargetIban(String sourceIban, String targetIban);
}