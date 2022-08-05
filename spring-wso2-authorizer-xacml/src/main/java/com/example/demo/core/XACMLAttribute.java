package com.example.demo.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class XACMLAttribute {

  public String getUrn() {
    return urn;
  }

  public void setUrn(final String urn) {
    this.urn = urn;
  }

  public XACMLDataType getDataType() {
    return dataType;
  }

  public void setDataType(final XACMLDataType dataType) {
    this.dataType = dataType;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(final Object value) {
    this.value = value;
  }

  public XACMLAttribute(final String urn, final XACMLDataType dataType, final Object value) {
    this.urn = urn;
    this.dataType = dataType;
    this.value = value;
  }

  private XACMLDataType dataType;
  private Object        value;
  private String        urn ;
}
