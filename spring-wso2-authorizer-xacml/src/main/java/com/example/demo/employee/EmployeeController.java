package com.example.demo.employee;

import java.util.List;

import com.example.demo.service.CrudAppController;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmployeeController extends CrudAppController<Employee> {

  private final EmployeeRepository repository ;


  EmployeeController(EmployeeRepository repository) {
    super(Employee.class);
    this.repository = repository;
  }

  //@PreAuthorize("hasPermission(#model, 'read')")
  @PreAuthorize("hasPermission('x','Employee', 'readXAll')")
  @PostFilter("@employeeSecurityService.filterResult(filterObject,authentication)")
  //  @PostFilter("hasPermission(filterObject, 'read')")
  @GetMapping("/employees")
  List<Employee> all() {
    return repository.findAll();
  }

  @PostMapping("/employees")
  Employee newEmployee(@RequestBody Employee newEmployee) {
    return repository.save(newEmployee);
  }

  // Single item

  @PreAuthorize("hasPermission(#model, 'read')")
  @GetMapping("/employees/{id}")
  Employee one(@PathVariable Long id) {

    return repository.findById(id)
                     .orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @PutMapping("/employees/{id}")
  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

    return repository.findById(id)
                     .map(employee -> {
                       employee.setName(newEmployee.getName());
                       employee.setRole(newEmployee.getRole());
                       return repository.save(employee);
                     })
                     .orElseGet(() -> {
                       newEmployee.setId(id);
                       return repository.save(newEmployee);
                     });
  }

  @DeleteMapping("/employees/{id}")
  void deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
  }
}