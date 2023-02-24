package com.quarkus.bootcamp.nttdata.domain.exceptions.customer;

public class BodyCorporateNotFoundException extends Exception {
  public BodyCorporateNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
