package io.xacml.pep.json.client.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xacml.json.model.*;
import io.xacml.pep.json.client.AuthZClient;
import io.xacml.pep.json.client.ClientConfiguration;
import io.xacml.pep.json.client.DefaultClientConfiguration;

/**
 * This class contains sample code using JAX-RS to invoke a Policy Decision Point.
 * It supports both the JSON Profile of XACML 1.0 and 1.1.
 */
public class AuthZClientExample {

    public static void main(String[] args) {
        final String authorizationServiceUrl = "https://localhost:9443/api/identity/entitlement/decision/pdp";
        final String username = "admin";
        final String password = "admin";

        ClientConfiguration clientConfiguration = DefaultClientConfiguration.builder()
                .authorizationServiceUrl(authorizationServiceUrl)
                .username(username)
                .password(password)
                .build();

        Request request = buildXACMLRequest();

        callPDPWithFeignClient(clientConfiguration, request);
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
