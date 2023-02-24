package com.quarkus.bootcamp.nttdata.domain.exceptions.address;

public class StateNotFoundException extends Exception {
  public StateNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
