package com.example.demo;

import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.authorization.method.PreAuthorizeAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig  {


  @Bean
  static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    CustomPermissionEvaluator evaluator = new CustomPermissionEvaluator();
    handler.setPermissionEvaluator(evaluator);
    return handler;
  }
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()

        // allow anonymous access to the root page
        .antMatchers("/").permitAll()
        // all other requests
        .anyRequest().authenticated()
        // 	Replace with logoutSuccessHandler(oidcLogoutSuccessHandler()) to support OIDC RP-initiated logout
        .and().logout().logoutSuccessHandler(oidcLogoutSuccessHandler())
        // enable OAuth2/OIDC
        .and().oauth2Login();

  }

  //Inject the ClientRegistrationRepository which stores client registration information
  @Autowired
  private ClientRegistrationRepository clientRegistrationRepository;

  /**
   * Create a OidcClientInitiatedLogoutSuccessHandler
   *
   * @return OidcClientInitiatedLogoutSuccessHandler
   */
  private LogoutSuccessHandler oidcLogoutSuccessHandler() {

    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
        new OidcClientInitiatedLogoutSuccessHandler(
            this.clientRegistrationRepository);

    oidcLogoutSuccessHandler.setPostLogoutRedirectUri(
        "http://localhost:8080"); //Need to give the post-rediret-uri here

    return oidcLogoutSuccessHandler;
  }

  //   @Bean
  //    static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
  //        DefaultMethodSecurityExpressionHandler expressionHandler =
  //            new DefaultMethodSecurityExpressionHandler();
  //        expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
  //        return expressionHandler;
  //    }
}
