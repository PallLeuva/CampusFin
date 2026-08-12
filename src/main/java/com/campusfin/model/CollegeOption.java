package com.campusfin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class CollegeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(
            message = "College name is required."
    )
    private String collegeName;

    @PositiveOrZero(
            message = "Tuition cannot be negative."
    )
    private double tuition;

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
            message = "Scholarship amount cannot be negative."
    )
    private double scholarship;

    @PositiveOrZero(
            message = "Family contribution cannot be negative."
    )
    private double familyContribution;

    @PositiveOrZero(
            message = "Student income cannot be negative."
    )
    private double studentIncome;

    private double annualCost;
    private double annualFundingGap;
    private double fourYearCost;
    private double fourYearFundingGap;
    private double scholarshipCoverage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }


    public double getTuition() {
        return tuition;
    }

    public void setTuition(double tuition) {
        this.tuition = tuition;
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


    public double getScholarship() {
        return scholarship;
    }

    public void setScholarship(double scholarship) {
        this.scholarship = scholarship;
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


    public double getAnnualCost() {
        return annualCost;
    }

    public void setAnnualCost(double annualCost) {
        this.annualCost = annualCost;
    }


    public double getAnnualFundingGap() {
        return annualFundingGap;
    }

    public void setAnnualFundingGap(double annualFundingGap) {
        this.annualFundingGap = annualFundingGap;
    }


    public double getFourYearCost() {
        return fourYearCost;
    }

    public void setFourYearCost(double fourYearCost) {
        this.fourYearCost = fourYearCost;
    }


    public double getFourYearFundingGap() {
        return fourYearFundingGap;
    }

    public void setFourYearFundingGap(double fourYearFundingGap) {
        this.fourYearFundingGap = fourYearFundingGap;
    }


    public double getScholarshipCoverage() {
        return scholarshipCoverage;
    }

    public void setScholarshipCoverage(double scholarshipCoverage) {
        this.scholarshipCoverage = scholarshipCoverage;
    }
}