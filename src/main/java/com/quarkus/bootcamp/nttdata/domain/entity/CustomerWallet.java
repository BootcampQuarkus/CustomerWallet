package com.quarkus.bootcamp.nttdata.domain.entity;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.address.AddressD;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.document.DocumentD;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@MongoEntity(collection = "customerwallet")
public class CustomerWallet {

    //@BsonId
    private ObjectId id;
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
