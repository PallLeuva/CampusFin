package com.campusfin.service;

import com.campusfin.model.FinancialReadinessInput;
import org.springframework.stereotype.Service;

@Service
public class FinancialReadinessService {

    public int calculateTotalScore(FinancialReadinessInput input) {

        int total =
                input.getBudgetingKnowledge()
                + input.getCollegeCostKnowledge()
                + input.getScholarshipKnowledge()
                + input.getCreditKnowledge()
                + input.getDebtKnowledge()
                + input.getEmergencyFundKnowledge()
                + input.getSavingsHabit()
                + input.getConfidenceLevel();

        return total;
    }

    public double calculatePercentageScore(FinancialReadinessInput input) {

        int totalScore = calculateTotalScore(input);

        int maximumScore = 40;

        return ((double) totalScore / maximumScore) * 100;
    }

    public String getReadinessLevel(FinancialReadinessInput input) {

        double score = calculatePercentageScore(input);

        if (score >= 80) {
            return "Highly Ready";
        }

        if (score >= 60) {
            return "Moderately Ready";
        }

        if (score >= 40) {
            return "Developing Readiness";
        }

        return "Needs Improvement";
    }

    public String getRecommendation(FinancialReadinessInput input) {

        double score = calculatePercentageScore(input);

        if (score >= 80) {
            return "You show strong financial readiness. Continue reviewing college costs, saving regularly, and comparing financial aid options.";
        }

        if (score >= 60) {
            return "You have a good foundation. Focus on improving weaker areas such as credit, debt, emergency savings, or college-cost planning.";
        }

        if (score >= 40) {
            return "You are developing financial readiness. Spend more time learning about budgeting, scholarships, credit, debt, and emergency funds.";
        }

        return "Build your financial foundation by learning basic budgeting, college costs, scholarships, savings, credit, and responsible debt management.";
    }
}