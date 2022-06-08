package com.example.demo;

import java.io.Serializable;

import io.xacml.json.model.Attribute;
import io.xacml.json.model.Category;
import io.xacml.json.model.Request;
import io.xacml.json.model.Response;
import io.xacml.json.model.Result;
import io.xacml.pep.json.client.AuthZClient;
import io.xacml.pep.json.client.ClientConfiguration;
import io.xacml.pep.json.client.DefaultClientConfiguration;
import io.xacml.pep.json.client.feign.FeignAuthZClient;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class CustomPermissionEvaluator implements PermissionEvaluator {

  @Override
  public boolean hasPermission(final Authentication authentication, final Serializable targetId, final String targetType, final Object permission) {
    return true;
  }


  @Override
  public boolean hasPermission(final Authentication authentication, final Object targetDomainObject, final Object permission) {
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
    return false;
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
