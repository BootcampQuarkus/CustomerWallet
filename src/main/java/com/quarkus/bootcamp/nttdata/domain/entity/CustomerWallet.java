package com.quarkus.bootcamp.nttdata.domain.entity;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@MongoEntity(collection = "customerwallet")
public class CustomerWallet {

    protected ObjectId id;
    protected String user;
    protected String cellphone;
    protected String password;
    protected double amount;
    protected String fullName;
    protected Long cardId = null;
    protected Long addressId;
    protected Long documentId;
    protected DocumentD document;
    protected AddressD address;
}