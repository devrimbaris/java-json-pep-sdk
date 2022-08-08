package com.example.demo.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import com.example.demo.core.XACMLDataType;
import com.example.demo.core.XACMLField;

@Entity
public class Employee {

  @Id
  @GeneratedValue
  @XACMLField(urn = "id", dataType = XACMLDataType.INTEGER)
  private Long   id;

  @XACMLField(urn = "name")
  private String name;

  @XACMLField(urn = "role")
  private String role;

  @XACMLField(urn = "age", dataType = XACMLDataType.INTEGER)
  private int    age;

  private String    gender;

  Employee() {
  }

  public Employee(String name, String role, int age) {

    this.name = name;
    this.role = role;
    this.age = age;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getRole() {
    return this.role;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public int getAge() {
    return age;
  }

  public void setAge(final int age) {
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (!(o instanceof Employee)) {
      return false;
    }
    Employee employee = (Employee) o;
    return Objects.equals(this.id, employee.id)
           && Objects.equals(this.name, employee.name)
           && Objects.equals(this.age, employee.age)
           && Objects.equals(this.role, employee.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.role, this.age);
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + this.id +
           ", name='" + this.name + '\'' +
           ", role='" + this.role + '\'' +
           ", age='" + this.age + '\'' +
           '}';
  }
}