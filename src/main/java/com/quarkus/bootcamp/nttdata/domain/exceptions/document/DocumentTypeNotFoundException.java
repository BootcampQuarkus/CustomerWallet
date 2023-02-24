package com.quarkus.bootcamp.nttdata.domain.exceptions.document;

public class DocumentTypeNotFoundException extends Exception {
  public DocumentTypeNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
