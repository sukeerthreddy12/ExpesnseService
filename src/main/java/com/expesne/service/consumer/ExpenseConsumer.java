package com.expesne.service.consumer;

import com.expesne.service.dto.ExpenseDto;
import com.expesne.service.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseConsumer {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseConsumer(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDto eventData) {
        try {
            expenseService.createOrUpdateExpense(eventData);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ExpenseConsumer: Exception is thrown while consuming kafka event: " + ex.getMessage());
        }

    }






}
