package com.campusfin.service;

import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.repository.StudentFinancialProfileRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class StudentFinancialProfileService {

    private static final String SESSION_PROFILE_ID =
            "studentFinancialProfileId";

    private final StudentFinancialProfileRepository repository;

    public StudentFinancialProfileService(
            StudentFinancialProfileRepository repository) {

        this.repository = repository;
    }


    public StudentFinancialProfile getOrCreateProfile(
            HttpSession session) {

        Long profileId =
                (Long) session.getAttribute(SESSION_PROFILE_ID);

        /*
         * First try to load the profile associated
         * with the current browser session.
         */
        if (profileId != null) {

            return repository.findById(profileId)
                    .orElseGet(() ->
                            createNewProfile(session));
        }


        /*
         * If Spring Boot was restarted, the browser
         * session may no longer contain the profile ID.
         *
         * In that case, load the most recently saved
         * CampusFin profile from the database.
         */
        StudentFinancialProfile profile =
                repository
                        .findTopByOrderByIdDesc()
                        .orElse(null);


        if (profile != null) {

            session.setAttribute(
                    SESSION_PROFILE_ID,
                    profile.getId());

            return profile;
        }


        return createNewProfile(session);
    }


    public StudentFinancialProfile saveProfile(
            StudentFinancialProfile profile,
            HttpSession session) {

        StudentFinancialProfile savedProfile =
                repository.save(profile);

        session.setAttribute(
                SESSION_PROFILE_ID,
                savedProfile.getId());

        /*
         * We keep this session attribute temporarily
         * because the existing CampusFin controllers
         * currently use it.
         */
        session.setAttribute(
                "studentFinancialProfile",
                savedProfile);

        return savedProfile;
    }


    public StudentFinancialProfile getSavedProfile(
            HttpSession session) {

        Long profileId =
                (Long) session.getAttribute(SESSION_PROFILE_ID);

        if (profileId != null) {

            StudentFinancialProfile profile =
                    repository.findById(profileId)
                            .orElse(null);

            if (profile != null) {

                session.setAttribute(
                        "studentFinancialProfile",
                        profile);

                return profile;
            }
        }


        StudentFinancialProfile profile =
                repository
                        .findTopByOrderByIdDesc()
                        .orElse(null);


        if (profile != null) {

            session.setAttribute(
                    SESSION_PROFILE_ID,
                    profile.getId());

            session.setAttribute(
                    "studentFinancialProfile",
                    profile);
        }


        return profile;
    }


    private StudentFinancialProfile createNewProfile(
            HttpSession session) {

        StudentFinancialProfile profile =
                new StudentFinancialProfile();

        StudentFinancialProfile savedProfile =
                repository.save(profile);

        session.setAttribute(
                SESSION_PROFILE_ID,
                savedProfile.getId());

        session.setAttribute(
                "studentFinancialProfile",
                savedProfile);

        return savedProfile;
    }
}
