package com.quarkus.bootcamp.nttdata.domain.exceptions.address;

public class AddressNotFoundException extends Exception {
  public AddressNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
