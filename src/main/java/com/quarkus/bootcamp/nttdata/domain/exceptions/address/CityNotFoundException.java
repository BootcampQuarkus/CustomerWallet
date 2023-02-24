package com.quarkus.bootcamp.nttdata.domain.exceptions.address;

public class CityNotFoundException extends Exception {
  public CityNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
