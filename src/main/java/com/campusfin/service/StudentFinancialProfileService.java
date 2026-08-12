package com.campusfin.service;

import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.model.User;
import com.campusfin.repository.StudentFinancialProfileRepository;
import com.campusfin.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StudentFinancialProfileService {

    private static final String SESSION_PROFILE_ID =
            "studentFinancialProfileId";

    private final StudentFinancialProfileRepository repository;
    private final UserRepository userRepository;

    public StudentFinancialProfileService(
            StudentFinancialProfileRepository repository,
            UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }


    // ----------------------------------------------------
    // Get existing profile or create one for logged-in user
    // ----------------------------------------------------

    public StudentFinancialProfile getOrCreateProfile(
            HttpSession session) {

        User currentUser = getCurrentUser();

        StudentFinancialProfile profile =
                repository
                        .findByUser(currentUser)
                        .orElseGet(() ->
                                createNewProfile(
                                        currentUser,
                                        session
                                )
                        );

        updateSession(
                profile,
                session
        );

        return profile;
    }


    // ----------------------------------------------------
    // Save Profile
    // ----------------------------------------------------

    public StudentFinancialProfile saveProfile(
            StudentFinancialProfile profile,
            HttpSession session) {

        User currentUser = getCurrentUser();

        /*
         * Make sure the profile always belongs
         * to the currently authenticated user.
         */
        profile.setUser(currentUser);

        StudentFinancialProfile savedProfile =
                repository.save(profile);

        updateSession(
                savedProfile,
                session
        );

        return savedProfile;
    }


    // ----------------------------------------------------
    // Get Saved Profile
    // ----------------------------------------------------

    public StudentFinancialProfile getSavedProfile(
            HttpSession session) {

        User currentUser = getCurrentUser();

        StudentFinancialProfile profile =
                repository
                        .findByUser(currentUser)
                        .orElse(null);

        if (profile != null) {

            updateSession(
                    profile,
                    session
            );
        } else {

            session.removeAttribute(
                    SESSION_PROFILE_ID
            );

            session.removeAttribute(
                    "studentFinancialProfile"
            );
        }

        return profile;
    }


    // ----------------------------------------------------
    // Create New Profile
    // ----------------------------------------------------

    private StudentFinancialProfile createNewProfile(
            User currentUser,
            HttpSession session) {

        StudentFinancialProfile profile =
                new StudentFinancialProfile();

        profile.setUser(currentUser);

        StudentFinancialProfile savedProfile =
                repository.save(profile);

        updateSession(
                savedProfile,
                session
        );

        return savedProfile;
    }


    // ----------------------------------------------------
    // Current Logged-In User
    // ----------------------------------------------------

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                        authentication.getPrincipal()
                )) {

            throw new IllegalStateException(
                    "A logged-in CampusFin account is required."
            );
        }

        String email =
                authentication
                        .getName()
                        .trim()
                        .toLowerCase();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Logged-in CampusFin user could not be found."
                        )
                );
    }


    // ----------------------------------------------------
    // Maintain Existing Session Attributes
    // ----------------------------------------------------

    private void updateSession(
            StudentFinancialProfile profile,
            HttpSession session) {

        session.setAttribute(
                SESSION_PROFILE_ID,
                profile.getId()
        );

        /*
         * Keep this temporarily because some existing
         * CampusFin controllers may still use it.
         */
        session.setAttribute(
                "studentFinancialProfile",
                profile
        );
    }
}