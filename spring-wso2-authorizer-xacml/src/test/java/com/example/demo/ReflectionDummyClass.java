package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import com.example.demo.core.XACMLAttribute;

@Entity
public class ReflectionDummyClass {

  @Id
  @GeneratedValue
  @XACMLAttribute(urn = "id")
  private Long   id;
  @XACMLAttribute(urn = "name")
  private String name;
  @XACMLAttribute(urn = "role")
  private String role;
  @XACMLAttribute(urn = "age")
  private int    age;

  private String    gender;

  ReflectionDummyClass() {
  }

  public ReflectionDummyClass(String name, String role, int age) {

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
    if (!(o instanceof ReflectionDummyClass)) {
      return false;
    }
    ReflectionDummyClass dummy = (ReflectionDummyClass) o;
    return Objects.equals(this.id, dummy.id)
           && Objects.equals(this.name, dummy.name)
           && Objects.equals(this.age, dummy.age)
           && Objects.equals(this.role, dummy.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.role, this.age);
  }

  @Override
  public String toString() {
    return "Dummy{" + "id=" + this.id +
           ", name='" + this.name + '\'' +
           ", role='" + this.role + '\'' +
           ", age='" + this.age + '\'' +
           '}';
  }
}