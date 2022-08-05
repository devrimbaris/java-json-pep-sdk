package com.example.demo.service;

import org.springframework.security.core.Authentication;

public interface ResultFilterer {

  public boolean filterResult(Object filterObject, Authentication authentication);

}
