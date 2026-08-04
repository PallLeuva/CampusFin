package com.campusfin.service;

import com.campusfin.model.CollegeCostInput;
import org.springframework.stereotype.Service;

@Service
public class CollegeCostService {
   public double calculateAnnualTotal(CollegeCostInput input) {
        return input.getTuition()
                + input.getHousing()
                + input.getFood()
                + input.getBooks()
                + input.getTransportation();
    }

    public double calculateAnnualFundingGap(CollegeCostInput input) {
        double totalCost = calculateAnnualTotal(input);

        double availableFunding = input.getScholarship()
                + input.getFamilyContribution()
                + input.getStudentIncome();

        return Math.max(totalCost - availableFunding, 0);
    }

    public double calculateFourYearCost(CollegeCostInput input) {
        return calculateAnnualTotal(input) * 4;
    }

    public double calculateFourYearFundingGap(CollegeCostInput input) {
        return calculateAnnualFundingGap(input) * 4;
    }

    public double calculateScholarshipCoverage(CollegeCostInput input) {
        double totalCost = calculateAnnualTotal(input);

        if (totalCost == 0) {
            return 0;
        }

        return input.getScholarship() / totalCost * 100;
    }  
}
