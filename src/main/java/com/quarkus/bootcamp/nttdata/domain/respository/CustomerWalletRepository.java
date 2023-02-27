package com.quarkus.bootcamp.nttdata.domain.respository;

import com.quarkus.bootcamp.nttdata.domain.entity.CustomerWallet;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerWalletRepository implements ReactivePanacheMongoRepository<CustomerWallet> {

}
