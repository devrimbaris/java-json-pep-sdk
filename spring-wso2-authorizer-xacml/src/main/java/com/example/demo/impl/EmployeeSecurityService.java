package com.example.demo.impl;

import com.example.demo.employee.Employee;
import com.example.demo.service.SecurityService;
import org.springframework.stereotype.Service;

@Service("employeeSecurityService")
public class EmployeeSecurityService extends SecurityService<Employee> {



}
