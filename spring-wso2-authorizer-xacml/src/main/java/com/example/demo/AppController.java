package com.example.demo;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String getProfile(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            DefaultOidcUser userDetails = (DefaultOidcUser) authentication.getPrincipal();
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("userName", userDetails.getName());
            model.addAttribute("IDTokenClaims", userDetails);
            model.addAttribute("AccessToken", authentication.getCredentials());
        }
        return "home";
    }

}
