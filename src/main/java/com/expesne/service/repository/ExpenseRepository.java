package com.expesne.service.repository;

import com.expesne.service.entities.Expense;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    List <Expense> findByUserId(String userId);

    List<Expense> findByUserIdAndCreatedAtBetween(String userId, java.sql.Timestamp startDate, java.sql.Timestamp endDate);

    Optional<Expense> findByUserIdAndExternalId(String userId, String externalId);


}
