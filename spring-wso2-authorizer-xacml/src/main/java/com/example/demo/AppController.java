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
//        final String authorizationServiceUrl = "https://localhost:9443/api/identity/entitlement/decision/pdp";
//        final String username = "admin";
//        final String password = "admin";
//
//        ClientConfiguration clientConfiguration = DefaultClientConfiguration.builder()
//                                                                            .authorizationServiceUrl(authorizationServiceUrl)
//                                                                            .username(username)
//                                                                            .password(password)
//                                                                            .build();
//
//        Request request = buildXACMLRequest();
//
//        callPDPWithFeignClient(clientConfiguration, request);

        if (authentication != null && authentication.isAuthenticated()) {
            DefaultOidcUser userDetails = (DefaultOidcUser) authentication.getPrincipal();
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("userName", userDetails.getName());
            model.addAttribute("IDTokenClaims", userDetails);
            model.addAttribute("AccessToken", authentication.getCredentials());
        }
        return "home";
    }


    private static Request buildXACMLRequest() {
        //build subject
        Category subject = new Category();
        subject.addAttribute(new Attribute("http://wso2.org/identity/user/username",
                                           "adminUser",
                                           false,
                                           "string"
        ));

        //build action
        Category action = new Category();
        action.addAttribute("urn:oasis:names:tc:xacml:1.0:action:action-id", "read");

        //build resource
        Category resource = new Category();
        resource.addAttribute("urn:oasis:names:tc:xacml:1.0:resource:resource-id", "http://127.0.0.1/service/very_secure/");

        //build request from subject, action and resource
        Request request = new Request();
        request.addAccessSubjectCategory(subject);
        request.addActionCategory(action);
        request.addResourceCategory(resource);
        return request;
    }

    private static void callPDPWithFeignClient(ClientConfiguration clientConfiguration, Request request) {
        System.out.println(clientConfiguration);
        try {
            AuthZClient authZClient   = new FeignAuthZClient(clientConfiguration);
            Response    xacmlResponse = authZClient.makeAuthorizationRequest(request);
            for (Result r : xacmlResponse.getResults()) {
                System.out.println("Decision: " + r.getDecision());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
