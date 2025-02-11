package io.xacml.pep.json.client.jaxrs;

import io.xacml.json.model.*;
import io.xacml.pep.json.client.AuthZClient;
import io.xacml.pep.json.client.ClientConfiguration;
import io.xacml.pep.json.client.DefaultClientConfiguration;
import io.xacml.pep.json.client.PDPConstants;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.*;

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

        callPDPWithJaxRsClient(clientConfiguration, request);
//        callPDPWithJaxRsClientStepByStep(clientConfiguration, request);
    }

    private static Request buildXACMLRequest() {
        Category subject = new Category();
        subject.addAttribute(new Attribute("http://wso2.org/identity/user/username",
                                           "adminUser",
                                           false,
                                           "string"
                                           ));

        subject.addAttribute(new Attribute("age", 13, false, "integer"));

        Category action = new Category();
        action.addAttribute(new Attribute("urn:oasis:names:tc:xacml:1.0:action:action-id", "readAll", false,"string"));


        Request request = new Request();
        request.addAccessSubjectCategory(subject);
        request.addActionCategory(action);
        return request;
    }

    private static void callPDPWithJaxRsClient(ClientConfiguration clientConfiguration, Request request) {
        AuthZClient authZClient = new JaxRsAuthZClient(clientConfiguration);
        Response xacmlResponse = authZClient.makeAuthorizationRequest(request);
        for (Result r : xacmlResponse.getResults()) {
            System.out.println("Decision: " + r.getDecision());
        }
    }

    /**
     * Show the full build of a JaxRs client and use it to get a PDP response
     */
    private static void callPDPWithJaxRsClientStepByStep(ClientConfiguration configuration, Request request) {
        Client client = ClientBuilder.newClient();
        // Enable, if needed, basic authentication
        if (null != configuration.getUsername()) {
            client.register(HttpAuthenticationFeature.basic(configuration.getUsername(), configuration.getPassword()));
        }
        WebTarget webTarget = client.target(configuration.getAuthorizationServiceUrl());
        Invocation.Builder builder = webTarget.request(PDPConstants.CONTENT_TYPE);

        Response xacmlResponse = builder.post(Entity.entity(request, "application/json"), Response.class);
        for (Result r : xacmlResponse.getResults()) {
            System.out.println("Decision: " + r.getDecision());
        }
    }
}
