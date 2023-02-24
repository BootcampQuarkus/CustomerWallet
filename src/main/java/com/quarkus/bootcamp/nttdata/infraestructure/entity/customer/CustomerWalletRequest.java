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
    private String user;
    private String cellphone;
    private String password;
    private double amount;
    private String fullName;
    private Long addressId;
    private Long documentId;
    private DocumentD document;
    private AddressD address;
}
