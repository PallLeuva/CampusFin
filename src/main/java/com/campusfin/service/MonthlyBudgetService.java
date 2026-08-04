package com.campusfin.service;

import com.campusfin.model.MonthlyBudgetInput;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MonthlyBudgetService {

    public double calculateTotalExpenses(MonthlyBudgetInput input) {
        return input.getHousing()
                + input.getFood()
                + input.getTransportation()
                + input.getBooks()
                + input.getEntertainment()
                + input.getSubscriptions()
                + input.getOtherExpenses();
    }

    public double calculateMonthlyBalance(MonthlyBudgetInput input) {
        return input.getMonthlyIncome() - calculateTotalExpenses(input);
    }

    public double calculateSavingsRate(MonthlyBudgetInput input) {
        if (input.getMonthlyIncome() <= 0) {
            return 0;
        }

        double balance = calculateMonthlyBalance(input);

        if (balance <= 0) {
            return 0;
        }

        return balance / input.getMonthlyIncome() * 100;
    }

    public double calculateEssentialExpenses(MonthlyBudgetInput input) {
        return input.getHousing()
                + input.getFood()
                + input.getTransportation()
                + input.getBooks();
    }

    public double calculateEmergencyCoverage(MonthlyBudgetInput input) {
        double essentialExpenses = calculateEssentialExpenses(input);

        if (essentialExpenses <= 0) {
            return 0;
        }

        return input.getEmergencySavings() / essentialExpenses;
    }

    public String findLargestExpenseCategory(MonthlyBudgetInput input) {
        Map<String, Double> expenses = new LinkedHashMap<>();

        expenses.put("Housing", input.getHousing());
        expenses.put("Food", input.getFood());
        expenses.put("Transportation", input.getTransportation());
        expenses.put("Books", input.getBooks());
        expenses.put("Entertainment", input.getEntertainment());
        expenses.put("Subscriptions", input.getSubscriptions());
        expenses.put("Other expenses", input.getOtherExpenses());

        String largestCategory = "None";
        double largestAmount = 0;

        for (Map.Entry<String, Double> expense : expenses.entrySet()) {
            if (expense.getValue() > largestAmount) {
                largestAmount = expense.getValue();
                largestCategory = expense.getKey();
            }
        }

        return largestCategory;
    }

    public String generateBudgetStatus(MonthlyBudgetInput input) {
        double balance = calculateMonthlyBalance(input);

        if (balance < 0) {
            return "Your estimated expenses are higher than your income.";
        }

        if (balance == 0) {
            return "Your budget is balanced, but there is no remaining amount for savings.";
        }

        double savingsRate = calculateSavingsRate(input);

        if (savingsRate < 10) {
            return "Your budget has a small surplus. Consider reducing discretionary expenses.";
        }

        if (savingsRate < 20) {
            return "Your budget has a healthy surplus and some room for savings.";
        }

        return "Your budget shows a strong surplus and savings potential.";
    }

    public String generateEmergencyFundStatus(MonthlyBudgetInput input) {
        double monthsCovered = calculateEmergencyCoverage(input);

        if (monthsCovered == 0) {
            return "No emergency-fund coverage was identified.";
        }

        if (monthsCovered < 1) {
            return "Your emergency savings cover less than one month of essential expenses.";
        }

        if (monthsCovered < 3) {
            return "Your emergency savings provide some protection but cover fewer than three months.";
        }

        return "Your emergency savings cover at least three months of essential expenses.";
    }
}