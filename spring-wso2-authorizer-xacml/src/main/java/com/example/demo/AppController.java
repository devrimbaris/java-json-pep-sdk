package com.example.demo;

import io.xacml.json.model.Attribute;
import io.xacml.json.model.Category;
import io.xacml.json.model.Request;
import io.xacml.json.model.Response;
import io.xacml.json.model.Result;
import io.xacml.pep.json.client.AuthZClient;
import io.xacml.pep.json.client.ClientConfiguration;
import io.xacml.pep.json.client.DefaultClientConfiguration;
import io.xacml.pep.json.client.feign.FeignAuthZClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AppController {

    @GetMapping("/")
    @PreAuthorize("hasPermission(#model, 'read')")
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
