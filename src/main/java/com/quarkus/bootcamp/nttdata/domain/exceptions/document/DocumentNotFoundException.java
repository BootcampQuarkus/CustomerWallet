package com.quarkus.bootcamp.nttdata.domain.exceptions.document;

public class DocumentNotFoundException extends Exception {
  public DocumentNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
