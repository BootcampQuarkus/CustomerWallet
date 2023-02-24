package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.services.CustomerWalletService;
import com.quarkus.bootcamp.nttdata.infraestructure.entity.customer.CustomerWalletRequest;
import com.quarkus.bootcamp.nttdata.infraestructure.dto.ResponseDto;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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

}
