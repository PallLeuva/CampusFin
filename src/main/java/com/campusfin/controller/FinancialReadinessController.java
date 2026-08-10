package com.campusfin.controller;

import com.campusfin.model.FinancialReadinessInput;
import com.campusfin.service.AiReadinessService;
import com.campusfin.service.FinancialReadinessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class FinancialReadinessController {

    private final FinancialReadinessService financialReadinessService;
    private final AiReadinessService aiReadinessService;

    public FinancialReadinessController(
            FinancialReadinessService financialReadinessService,
            AiReadinessService aiReadinessService) {

        this.financialReadinessService = financialReadinessService;
        this.aiReadinessService = aiReadinessService;
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

        try {

            Map<String, Object> aiResult =
                    aiReadinessService.getPrediction(
                            financialReadinessInput
                    );

            model.addAttribute(
                    "aiPrediction",
                    aiResult.get("prediction")
            );

            model.addAttribute(
                    "aiConfidence",
                    aiResult.get("confidence")
            );

            model.addAttribute(
                    "aiAvailable",
                    true
            );

        } catch (Exception exception) {

            model.addAttribute(
                    "aiAvailable",
                    false
            );

            model.addAttribute(
                    "aiError",
                    "The AI prediction service is currently unavailable."
            );
        }

        return "financial-readiness";
    }
}