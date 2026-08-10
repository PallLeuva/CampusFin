package com.campusfin.controller;

import com.campusfin.model.FinancialReadinessInput;
import com.campusfin.service.FinancialReadinessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FinancialReadinessController {

    private final FinancialReadinessService financialReadinessService;

    public FinancialReadinessController(
            FinancialReadinessService financialReadinessService) {

        this.financialReadinessService = financialReadinessService;
    }

    @GetMapping("/financial-readiness")
    public String showAssessment(Model model) {

        model.addAttribute(
                "financialReadinessInput",
                new FinancialReadinessInput()
        );

        return "financial-readiness";
    }

    @PostMapping("/financial-readiness")
    public String calculateReadiness(
            FinancialReadinessInput financialReadinessInput,
            Model model) {

        model.addAttribute(
                "financialReadinessInput",
                financialReadinessInput
        );

        model.addAttribute(
                "percentageScore",
                financialReadinessService.calculatePercentageScore(
                        financialReadinessInput)
        );

        model.addAttribute(
                "readinessLevel",
                financialReadinessService.getReadinessLevel(
                        financialReadinessInput)
        );

        model.addAttribute(
                "strongestArea",
                financialReadinessService.getStrongestArea(
                        financialReadinessInput)
        );

        model.addAttribute(
                "weakestArea",
                financialReadinessService.getWeakestArea(
                        financialReadinessInput)
        );

        model.addAttribute(
                "recommendation",
                financialReadinessService.getRecommendation(
                        financialReadinessInput)
        );

        return "financial-readiness";
    }
}