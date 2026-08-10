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

        double percentageScore =
                financialReadinessService.calculatePercentageScore(
                        financialReadinessInput
                );

        String readinessLevel =
                financialReadinessService.getReadinessLevel(
                        financialReadinessInput
                );

        model.addAttribute(
                "percentageScore",
                percentageScore
        );

        model.addAttribute(
                "readinessLevel",
                readinessLevel
        );

        model.addAttribute(
                "strongestArea",
                financialReadinessService.getStrongestArea(
                        financialReadinessInput
                )
        );

        model.addAttribute(
                "weakestArea",
                financialReadinessService.getWeakestArea(
                        financialReadinessInput
                )
        );

        model.addAttribute(
                "recommendation",
                financialReadinessService.getRecommendation(
                        financialReadinessInput
                )
        );

        try {

            Map<String, Object> aiResult =
                    aiReadinessService.getPrediction(
                            financialReadinessInput
                    );

            String aiPrediction =
                    String.valueOf(
                            aiResult.get("prediction")
                    );

            Object aiConfidence =
                    aiResult.get("confidence");

            model.addAttribute(
                    "aiPrediction",
                    aiPrediction
            );

            model.addAttribute(
                    "aiConfidence",
                    aiConfidence
            );

            model.addAttribute(
                    "aiAvailable",
                    true
            );

            boolean modelsAgree =
                    readinessLevel.equalsIgnoreCase(aiPrediction);

            model.addAttribute(
                    "modelsAgree",
                    modelsAgree
            );

            if (modelsAgree) {

                model.addAttribute(
                        "comparisonMessage",
                        "Both models reached the same readiness level. This provides consistent results across the rule-based and machine-learning approaches."
                );

            } else {

                model.addAttribute(
                        "comparisonMessage",
                        "The two models produced different readiness levels. This difference can help identify where the rule-based and machine-learning approaches evaluate the same responses differently."
                );
            }

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