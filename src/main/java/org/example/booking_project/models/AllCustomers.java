package org.example.booking_project.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AllCustomers {
    public List<ContractCustomer> customers;

}
