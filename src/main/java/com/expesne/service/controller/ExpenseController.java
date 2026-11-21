package com.expesne.service.controller;

import com.expesne.service.dto.ExpenseDto;
import com.expesne.service.service.ExpenseService;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/expense/v1")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @GetMapping("/expense/v1/")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@RequestParam("userId") @NonNull String userId) {
        try {
            List<ExpenseDto> expenses = expenseService.getExpenses(userId);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/addExpense")
    public ResponseEntity<Boolean> addExpense(@RequestHeader(value = "X-User-Id") @Nonnull String userId, @RequestBody ExpenseDto expenseDto) {
        try {
            expenseDto.setUserId(userId);
            return new ResponseEntity<>(expenseService.createExpense(expenseDto), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}