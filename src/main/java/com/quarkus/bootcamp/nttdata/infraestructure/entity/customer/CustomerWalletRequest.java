package com.quarkus.bootcamp.nttdata.infraestructure.entity.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class CustomerWalletRequest {
    //private Long id;
    protected String user;
    protected String cellphone;
    protected String password;
    protected double amount;
    protected String fullName;
    protected Long addressId;
    protected Long documentId;
    protected DocumentD document;
    protected AddressD address;
    protected Long cardId;
}
