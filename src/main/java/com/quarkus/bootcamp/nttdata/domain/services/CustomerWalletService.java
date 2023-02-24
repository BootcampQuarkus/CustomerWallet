package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.domain.entity.CustomerWallet;
import com.quarkus.bootcamp.nttdata.domain.respository.CustomerWalletRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.CustomerWalletRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CustomerWalletService {

    @Inject
    CustomerWalletRepository customerWalletRepository;

    public Uni<CustomerWallet> add(CustomerWalletRequest customerWallet) {
        Uni<CustomerWallet> findUser = findCustomerWalletByUser(customerWallet.getUser());
        return findUser.onItem().transformToUni(user -> {
            if (user != null) {
                throw new NotFoundException("user is already registered");
            }
            Uni<CustomerWallet> findCellphone = findCustomerWalletByCellphone(customerWallet.getCellphone());
            return findCellphone.onItem().transformToUni(cellphone -> {
                if (cellphone != null) {
                    throw new NotFoundException("cellphone is already registered");
                }
                return saveUserAccount(customerWallet);
            });
        });
    }

    public Uni<CustomerWallet> findCustomerWalletByUser(String user) {
        return customerWalletRepository.find("{'user': ?1}", user).firstResult();
    }

    public Uni<CustomerWallet> findCustomerWalletByCellphone(String cellphone) {
        return customerWalletRepository.find("{'cellphone': ?1}", cellphone).firstResult();
    }

    public Uni<CustomerWallet> saveUserAccount(CustomerWalletRequest customerWallet) {
        CustomerWallet customerWalletPersist = new CustomerWallet();
        customerWalletPersist.setUser(customerWallet.getUser());
        customerWalletPersist.setCellphone(customerWallet.getCellphone());
        customerWalletPersist.setPassword(customerWallet.getPassword());
        customerWalletPersist.setAmount(customerWallet.getAmount());
        customerWalletPersist.setFullName(customerWallet.getFullName());
        return customerWalletRepository.persist(customerWalletPersist);
    }

    public Uni<CustomerWallet> login(CustomerWalletRequest customerWallet) {
        Uni<CustomerWallet> userWallet = findCustomerWalletByUser(customerWallet.getUser());
        return userWallet.onItem().transformToUni(c -> {
            if (c == null || c.getId() == null) {
                throw new NotFoundException("user wallet not found");
            } else {
                return customerWalletRepository.findById(c.getId());
            }
        });
    }
}
