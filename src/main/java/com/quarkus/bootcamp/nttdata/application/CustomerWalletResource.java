package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.entity.CustomerWallet;
import com.quarkus.bootcamp.nttdata.domain.services.CustomerWalletService;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.CustomerWalletRequest;
import com.quarkus.bootcamp.nttdata.infraestructure.dto.ResponseDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customerwallet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerWalletResource {

    @Inject
    CustomerWalletService customerWalletService;

    @POST
    @Path("/register")
    @Transactional
    public Uni<Response> register(CustomerWalletRequest customerWallet) {
        return customerWalletService.add(customerWallet)
                .onItem().transform(uri ->
                        Response.ok(new ResponseDto<>(200, "Se registro correctamente", uri)).status(200).build());
    }

    @POST
    @Path("/login")
    public Uni<Response> login(CustomerWalletRequest customerWallet) {
        return customerWalletService.login(customerWallet).onItem().transform(cw -> {
            if (cw.getPassword().equals(customerWallet.getPassword())) {
                return Response.ok(new ResponseDto<>(200, "login successed")).status(200).build();
            } else {
                return Response.ok(new ResponseDto<>(422, "login fault")).status(422).build();
            }
        });
    }

    @GET
    public Multi<CustomerWallet> list() {
        return customerWalletService.streamAllPosts();
    }

    @GET
    @Path("/{id}")
    public Uni<Products> getAll(@PathParam("id") Long id) {
        return service.getAll(id);
    }

}
