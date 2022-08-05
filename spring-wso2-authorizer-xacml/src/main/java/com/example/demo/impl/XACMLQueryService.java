package com.example.demo.impl;

import io.xacml.json.model.PDPDecision;
import io.xacml.json.model.Request;
import io.xacml.json.model.Response;
import io.xacml.json.model.Result;
import io.xacml.pep.json.client.AuthZClient;
import io.xacml.pep.json.client.ClientConfiguration;
import io.xacml.pep.json.client.DefaultClientConfiguration;
import io.xacml.pep.json.client.feign.FeignAuthZClient;
import org.springframework.stereotype.Service;

@Service("xacmlQueryService")
public final class XACMLQueryService {

  public boolean checkPermission(final Request request) {
    var clientConfiguration = buildClientConfiguration();
    final Response response = callPDPWithFeignClient(clientConfiguration, request);
    for(Result r: response.getResults()){
      if (!r.getDecision().equals(PDPDecision.PERMIT))
        return false;
    }
    return true;
  }

  private ClientConfiguration buildClientConfiguration() {
    final String authorizationServiceUrl = "https://localhost:9443/api/identity/entitlement/decision/pdp";
    final String username                = "admin";
    final String password                = "admin";

    ClientConfiguration clientConfiguration = DefaultClientConfiguration.builder()
                                                                        .authorizationServiceUrl(authorizationServiceUrl)
                                                                        .username(username)
                                                                        .password(password)
                                                                        .build();
    return clientConfiguration;
  }

  private Response callPDPWithFeignClient(ClientConfiguration clientConfiguration, Request request) {
    System.out.println(clientConfiguration);
    AuthZClient authZClient   = new FeignAuthZClient(clientConfiguration);
    Response    xacmlResponse = authZClient.makeAuthorizationRequest(request);
//    for (Result r : xacmlResponse.getResults()) {
//      System.out.println("Decision: " + r.getDecision());
//    }
    return xacmlResponse;
  }
  //  protected static Request buildXACMLRequest() {
  //    //build subject
  //    Category subject = new Category();
  //    subject.addAttribute(new Attribute("http://wso2.org/identity/user/username",
  //                                       "adminUser",
  //                                       false,
  //                                       "string"
  //    ));
  //
  //    //build action
  //    Category action = new Category();
  //    action.addAttribute("urn:oasis:names:tc:xacml:1.0:action:action-id", "read");
  //
  //    //build resource
  //    Category resource = new Category();
  //    resource.addAttribute("urn:oasis:names:tc:xacml:1.0:resource:resource-id", "http://127.0.0.1/service/very_secure/");
  //
  //    //build request from subject, action and resource
  //    Request request = new Request();
  //    request.addAccessSubjectCategory(subject);
  //    request.addActionCategory(action);
  //    request.addResourceCategory(resource);
  //    return request;
  //  }
  //  public boolean hasPermission(final Authentication authentication, final Object targetDomainObject, final Object permission) {
  //
  //    Request request             = buildXACMLRequest(targetDomainObject.getClass().getCanonicalName(), authentication.getName(), permission.toString());
  //    var     clientConfiguration = buildClientConfiguration();
  //    callPDPWithFeignClient(clientConfiguration, request);
  //    return true;
  //  }
  //
  //  public boolean hasPermission(final Authentication authentication, final Serializable targetId, final String resourceTargetType, final Object permission) {
  //    try {
  //      String  strPermission       = (String) permission;
  //      Request request             = buildXACMLRequest(resourceTargetType, authentication.getName(), strPermission);
  //      var     clientConfiguration = buildClientConfiguration();
  //      callPDPWithFeignClient(clientConfiguration, request);
  //    }
  //    catch (Exception cls) {
  //      cls.printStackTrace();
  //      return false;
  //    }
  //    return true;
  //  }

}
