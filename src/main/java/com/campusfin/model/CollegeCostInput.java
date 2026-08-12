package com.campusfin.model;

import jakarta.validation.constraints.PositiveOrZero;

public class CollegeCostInput {

    @PositiveOrZero(
            message = "Tuition cannot be negative."
    )
    private double tuition;

    @PositiveOrZero(
            message = "Scholarship amount cannot be negative."
    )
    private double scholarship;

    @PositiveOrZero(
            message = "Housing cost cannot be negative."
    )
    private double housing;

    @PositiveOrZero(
            message = "Food cost cannot be negative."
    )
    private double food;

    @PositiveOrZero(
            message = "Books cost cannot be negative."
    )
    private double books;

    @PositiveOrZero(
            message = "Transportation cost cannot be negative."
    )
    private double transportation;

    @PositiveOrZero(
            message = "Family contribution cannot be negative."
    )
    private double familyContribution;

    @PositiveOrZero(
            message = "Student income cannot be negative."
    )
    private double studentIncome;


    public double getTuition() {
        return tuition;
    }

    public void setTuition(double tuition) {
        this.tuition = tuition;
    }


    public double getScholarship() {
        return scholarship;
    }

    public void setScholarship(double scholarship) {
        this.scholarship = scholarship;
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


    public double getBooks() {
        return books;
    }

    public void setBooks(double books) {
        this.books = books;
    }


    public double getTransportation() {
        return transportation;
    }

    public void setTransportation(double transportation) {
        this.transportation = transportation;
    }


    public double getFamilyContribution() {
        return familyContribution;
    }

    public void setFamilyContribution(double familyContribution) {
        this.familyContribution = familyContribution;
    }


    public double getStudentIncome() {
        return studentIncome;
    }

    public void setStudentIncome(double studentIncome) {
        this.studentIncome = studentIncome;
    }
}