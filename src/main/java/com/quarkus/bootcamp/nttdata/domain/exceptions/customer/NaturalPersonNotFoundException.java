package com.quarkus.bootcamp.nttdata.domain.exceptions.customer;

public class NaturalPersonNotFoundException extends Exception {
  public NaturalPersonNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
