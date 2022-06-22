package com.example.demo;

import com.example.demo.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityMetadataSourceAdvisor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {


  public boolean filterResult(Object filterObject ){
    Employee x = (Employee)filterObject;
    if (x.getRole().equals("burglar"))
      return true;

    return false;

  }
}
