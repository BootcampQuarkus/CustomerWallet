package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.infraestructure.entity.card.Card;
import com.quarkus.bootcamp.nttdata.domain.entity.CustomerWallet;
import com.quarkus.bootcamp.nttdata.domain.services.CustomerWalletService;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.Amount;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.CustomerWalletRequest;
import com.quarkus.bootcamp.nttdata.infraestructure.dto.ResponseDto;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customerwallet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerWalletResource {

    @Inject
    CustomerWalletService service;

    @POST
    @Path("/register")
    @Transactional
    public Uni<Response> register(CustomerWalletRequest customerWallet) {
        return service.add(customerWallet)
                .onItem().transform(uri ->
                        Response.ok(new ResponseDto<>(200, "Se registro correctamente", uri)).status(200).build());
    }

    @POST
    @Path("/login")
    public Uni<Response> login(CustomerWalletRequest customerWallet) {
        return service.login(customerWallet).onItem().transform(cw -> {
            if (cw.getPassword().equals(customerWallet.getPassword())) {
                return Response.ok(new ResponseDto<>(200, "login successed")).status(200).build();
            } else {
                return Response.ok(new ResponseDto<>(422, "login fault")).status(422).build();
            }
        });
    }

    @GET
    public Uni<List<CustomerWallet>> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Uni<CustomerWallet> getById(@PathParam("id") String id) {
        return service.getById(id);
    }

    @PUT
    @Path("/card/{id}")
    @Transactional
    public Uni<CustomerWallet> updateCardId(@PathParam("id") String id, Card card) {
        return service.updateCardId(id, card);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Uni<Response> delete(@PathParam("id") String id) {
        service.delete(id);
        return Uni.createFrom().item(Response.ok().build());
    }

    @GET
    @Path("/cellphone/{cellphone}")
    public Uni<CustomerWallet> viewCustomerByCellphone(@PathParam("cellphone") String cellphone) {
        Uni<CustomerWallet> entity = service.findByCellphone(cellphone);
        if (entity == null) {
            throw new WebApplicationException("Loan with " + cellphone + " does not exist.", 404);
        }
        return entity;
    }

    @PUT
    @Path("/amount/{id}")
    @Transactional
    public Uni<CustomerWallet> updateAmount(@PathParam("id") String id, Amount amount) {
        return service.updateAmount(id, amount);
    }
}