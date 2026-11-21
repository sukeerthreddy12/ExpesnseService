package com.expesne.service.service;


import com.expesne.service.dto.ExpenseDto;
import com.expesne.service.entities.Expense;
import com.expesne.service.repository.ExpenseRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public boolean createOrUpdateExpense(ExpenseDto expenseDto) {
        if (updateExpense(expenseDto)) {
            return true;
        }
        return createExpense(expenseDto);
    }

    public boolean createExpense(ExpenseDto expenseDto){
        setcurrency(expenseDto);
        try {
            Expense expense = new Expense();
            expense.setUserId(expenseDto.getUserId());
            expense.setMerchant(expenseDto.getMerchant());
            expense.setCurrency(expenseDto.getCurrency());
            if (expenseDto.getAmount() != null) {
                expense.setAmount(new BigDecimal(expenseDto.getAmount()));
            }
            expenseRepository.save(expense);
            return true ;
        }catch(Exception e ){
            e.printStackTrace();
            return false ;
        }

    }

    public boolean updateExpense(ExpenseDto expenseDto){
       if (expenseDto.getUserId() == null || expenseDto.getExternalId() == null) {
           return false;
       }
       Optional<Expense> expenseOptional = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId() , expenseDto.getExternalId());
       if(expenseOptional.isEmpty()){
           return false;

       }
       Expense expense = expenseOptional.get();
       expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency()) ? expenseDto.getCurrency() : expense.getCurrency());
       expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant()) ? expenseDto.getMerchant() : expense.getMerchant());
       expense.setAmount(expenseDto.getAmount() == null ? null : new BigDecimal(expenseDto.getAmount()));
       expenseRepository.save(expense);
       return true;

    }

    public List<ExpenseDto> getExpenses(String userId){
        List<Expense> expenseList = expenseRepository.findByUserId(userId);

        return objectMapper.convertValue(expenseList, new TypeReference<List<ExpenseDto>>() {});
    }

    private void setcurrency(ExpenseDto expenseDto){
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("AUD");
        }

    }



}
