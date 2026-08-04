package com.campusfin.controller;

import com.campusfin.model.MonthlyBudgetInput;
import com.campusfin.service.MonthlyBudgetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MonthlyBudgetController {

    private final MonthlyBudgetService monthlyBudgetService;

    public MonthlyBudgetController(
            MonthlyBudgetService monthlyBudgetService) {

        this.monthlyBudgetService = monthlyBudgetService;
    }

    @GetMapping("/monthly-budget")
    public String showBudgetPlanner(Model model) {

        model.addAttribute(
                "monthlyBudgetInput",
                new MonthlyBudgetInput()
        );

        return "monthly-budget";
    }

    @PostMapping("/monthly-budget")
    public String calculateBudget(
            MonthlyBudgetInput monthlyBudgetInput,
            Model model) {

        model.addAttribute(
                "monthlyBudgetInput",
                monthlyBudgetInput
        );

        model.addAttribute(
                "totalExpenses",
                monthlyBudgetService.calculateTotalExpenses(
                        monthlyBudgetInput)
        );

        model.addAttribute(
                "monthlyBalance",
                monthlyBudgetService.calculateMonthlyBalance(
                        monthlyBudgetInput)
        );

        model.addAttribute(
                "savingsRate",
                monthlyBudgetService.calculateSavingsRate(
                        monthlyBudgetInput)
        );

        model.addAttribute(
                "emergencyCoverage",
                monthlyBudgetService.calculateEmergencyCoverage(
                        monthlyBudgetInput)
        );

        model.addAttribute(
                "largestExpenseCategory",
                monthlyBudgetService.findLargestExpenseCategory(
                        monthlyBudgetInput)
        );

        model.addAttribute(
                "budgetStatus",
                monthlyBudgetService.generateBudgetStatus(
                        monthlyBudgetInput)
        );

        model.addAttribute(
                "emergencyFundStatus",
                monthlyBudgetService.generateEmergencyFundStatus(
                        monthlyBudgetInput)
        );

        return "monthly-budget";
    }
}