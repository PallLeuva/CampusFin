package com.campusfin.controller;

import com.campusfin.model.CollegeCostInput;
import com.campusfin.service.CollegeCostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CollegeCostController {

    private final CollegeCostService collegeCostService;

    public CollegeCostController(CollegeCostService collegeCostService) {
        this.collegeCostService = collegeCostService;
    }

    @GetMapping("/college-cost")
    public String showCalculator(Model model) {
        model.addAttribute("collegeCostInput", new CollegeCostInput());
        return "college-cost";
    }

    @PostMapping("/college-cost")
    public String calculateCost(
            CollegeCostInput collegeCostInput,
            Model model) {

        model.addAttribute("collegeCostInput", collegeCostInput);
        model.addAttribute(
                "annualTotal",
                collegeCostService.calculateAnnualTotal(collegeCostInput)
        );
        model.addAttribute(
                "annualFundingGap",
                collegeCostService.calculateAnnualFundingGap(collegeCostInput)
        );
        model.addAttribute(
                "fourYearCost",
                collegeCostService.calculateFourYearCost(collegeCostInput)
        );
        model.addAttribute(
                "fourYearFundingGap",
                collegeCostService.calculateFourYearFundingGap(collegeCostInput)
        );
        model.addAttribute(
                "scholarshipCoverage",
                collegeCostService.calculateScholarshipCoverage(collegeCostInput)
        );

        return "college-cost";
    }
}