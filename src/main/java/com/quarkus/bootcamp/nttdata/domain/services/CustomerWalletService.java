package com.quarkus.bootcamp.nttdata.domain.services;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.card.Card;
import com.quarkus.bootcamp.nttdata.domain.entity.CustomerWallet;
import com.quarkus.bootcamp.nttdata.domain.respository.CustomerWalletRepository;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.Amount;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.CustomerWalletRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

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

    public Uni<List<CustomerWallet>> getAll() {
        return customerWalletRepository.listAll();
    }

    public Uni<CustomerWallet> getById(String id) {
        return customerWalletRepository.findById(new ObjectId(id));
    }

    public Uni<CustomerWallet> updateCardId(String id, Card card) {
        Uni<CustomerWallet> custWallet = customerWalletRepository.findById(new ObjectId(id));
        return custWallet
                .onItem().transform(au -> {
                    au.setCardId(card.getCardId());
                    return au;
                }).call(au -> customerWalletRepository.persistOrUpdate(au));
    }

    public Uni<Void> delete(String id) {
        Uni<CustomerWallet> custWallet = customerWalletRepository.findById(new ObjectId(id));
        return custWallet.call(cust -> customerWalletRepository.delete(cust)).replaceWithVoid();
    }

    public Uni<CustomerWallet> findByCellphone(String cellphone) {
        return customerWalletRepository.find("cellphone", cellphone).firstResult();
    }

    public Uni<CustomerWallet> updateAmount(String id, Amount amount) {
        boolean flagCompensation = true;
        Uni<CustomerWallet> custWallet = customerWalletRepository.findById(new ObjectId(id));
        if (amount.getOperation().equalsIgnoreCase("Discount")) {
            flagCompensation = false;
        } else if (amount.getOperation().equalsIgnoreCase("Compensation")) {
            flagCompensation = true;
        }
        boolean finalFlagCompensation = flagCompensation;
        return custWallet
                .onItem().transform(au -> {
                    if (finalFlagCompensation == true) {
                        au.setAmount(au.getAmount() + amount.getAmount());
                    } else {
                        au.setAmount(au.getAmount() - amount.getAmount());
                    }
                    return au;
                }).call(au -> customerWalletRepository.persistOrUpdate(au));
    }

}
