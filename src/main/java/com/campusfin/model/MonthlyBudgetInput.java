package com.campusfin.model;

import jakarta.validation.constraints.PositiveOrZero;

public class MonthlyBudgetInput {

    @PositiveOrZero(
            message = "Monthly income cannot be negative."
    )
    private double monthlyIncome;

    @PositiveOrZero(
            message = "Housing expense cannot be negative."
    )
    private double housing;

    @PositiveOrZero(
            message = "Food expense cannot be negative."
    )
    private double food;

    @PositiveOrZero(
            message = "Transportation expense cannot be negative."
    )
    private double transportation;

    @PositiveOrZero(
            message = "Books expense cannot be negative."
    )
    private double books;

    @PositiveOrZero(
            message = "Entertainment expense cannot be negative."
    )
    private double entertainment;

    @PositiveOrZero(
            message = "Subscription expense cannot be negative."
    )
    private double subscriptions;

    @PositiveOrZero(
            message = "Other expenses cannot be negative."
    )
    private double otherExpenses;

    @PositiveOrZero(
            message = "Emergency savings cannot be negative."
    )
    private double emergencySavings;


    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }


    public double getHousing() {
        return housing;
    }

    public void setHousing(double housing) {
        this.housing = housing;
    }


    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }


    public double getTransportation() {
        return transportation;
    }

    public void setTransportation(double transportation) {
        this.transportation = transportation;
    }


    public double getBooks() {
        return books;
    }

    public void setBooks(double books) {
        this.books = books;
    }


    public double getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(double entertainment) {
        this.entertainment = entertainment;
    }


    public double getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(double subscriptions) {
        this.subscriptions = subscriptions;
    }


    public double getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(double otherExpenses) {
        this.otherExpenses = otherExpenses;
    }


    public double getEmergencySavings() {
        return emergencySavings;
    }

    public void setEmergencySavings(double emergencySavings) {
        this.emergencySavings = emergencySavings;
    }
}