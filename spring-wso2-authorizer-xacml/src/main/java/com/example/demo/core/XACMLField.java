package com.example.demo.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XACMLField {
  public String urn() default "";
  public XACMLDataType dataType() default XACMLDataType.STRING;
}
