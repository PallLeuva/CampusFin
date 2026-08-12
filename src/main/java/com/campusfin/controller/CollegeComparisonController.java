package com.campusfin.controller;

import com.campusfin.model.CollegeOption;
import com.campusfin.repository.CollegeOptionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
public class CollegeComparisonController {

    private final CollegeOptionRepository collegeOptionRepository;

    public CollegeComparisonController(
            CollegeOptionRepository collegeOptionRepository) {

        this.collegeOptionRepository = collegeOptionRepository;
    }

    @GetMapping("/college-comparison")
    public String showCollegeComparison(Model model) {

        model.addAttribute(
                "collegeOption",
                new CollegeOption()
        );

        loadComparisonData(model);

        return "college-comparison";
    }

    @PostMapping("/college-comparison/add")
    public String addCollege(
            @ModelAttribute CollegeOption collegeOption) {

        double annualCost =
                collegeOption.getTuition()
                        + collegeOption.getHousing()
                        + collegeOption.getFood()
                        + collegeOption.getBooks()
                        + collegeOption.getTransportation();

        double annualFunding =
                collegeOption.getScholarship()
                        + collegeOption.getFamilyContribution()
                        + collegeOption.getStudentIncome();

        double annualFundingGap =
                Math.max(
                        annualCost - annualFunding,
                        0
                );

        double fourYearCost =
                annualCost * 4;

        double fourYearFundingGap =
                annualFundingGap * 4;

        double scholarshipCoverage = 0;

        if (annualCost > 0) {

            scholarshipCoverage =
                    collegeOption.getScholarship()
                            / annualCost
                            * 100;
        }

        collegeOption.setAnnualCost(
                annualCost
        );

        collegeOption.setAnnualFundingGap(
                annualFundingGap
        );

        collegeOption.setFourYearCost(
                fourYearCost
        );

        collegeOption.setFourYearFundingGap(
                fourYearFundingGap
        );

        collegeOption.setScholarshipCoverage(
                scholarshipCoverage
        );

        collegeOptionRepository.save(
                collegeOption
        );

        return "redirect:/college-comparison";
    }

    @PostMapping("/college-comparison/delete")
    public String deleteCollege(
            @RequestParam Long id) {

        collegeOptionRepository.deleteById(
                id
        );

        return "redirect:/college-comparison";
    }

    private void loadComparisonData(
            Model model) {

        List<CollegeOption> colleges =
                collegeOptionRepository.findAll();

        colleges.sort(
                Comparator.comparingDouble(
                        CollegeOption::getFourYearFundingGap
                )
        );

        model.addAttribute(
                "colleges",
                colleges
        );

        model.addAttribute(
                "comparisonAvailable",
                colleges.size() >= 2
        );

        if (!colleges.isEmpty()) {

            CollegeOption lowestCost =
                    colleges.stream()
                            .min(
                                    Comparator.comparingDouble(
                                            CollegeOption::getFourYearCost
                                    )
                            )
                            .orElse(null);

            CollegeOption lowestFundingGap =
                    colleges.stream()
                            .min(
                                    Comparator.comparingDouble(
                                            CollegeOption::getFourYearFundingGap
                                    )
                            )
                            .orElse(null);

            CollegeOption highestScholarshipCoverage =
                    colleges.stream()
                            .max(
                                    Comparator.comparingDouble(
                                            CollegeOption::getScholarshipCoverage
                                    )
                            )
                            .orElse(null);

            model.addAttribute(
                    "lowestCostCollege",
                    lowestCost
            );

            model.addAttribute(
                    "lowestFundingGapCollege",
                    lowestFundingGap
            );

            model.addAttribute(
                    "highestScholarshipCollege",
                    highestScholarshipCoverage
            );
        }
    }
}