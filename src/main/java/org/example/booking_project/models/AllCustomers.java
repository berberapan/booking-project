package org.example.booking_project.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "allcustomers")
public class AllCustomers {
    public List<ContractCustomer> customers;

}
