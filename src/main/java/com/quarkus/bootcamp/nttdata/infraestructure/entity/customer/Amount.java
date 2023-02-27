package com.quarkus.bootcamp.nttdata.infraestructure.entity.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Amount {
    protected double amount;
    protected String operation;
}
