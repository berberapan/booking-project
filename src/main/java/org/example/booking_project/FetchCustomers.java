package org.example.booking_project;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.booking_project.models.AllCustomers;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.service.impl.ContractCustomerServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import java.net.URL;

@ComponentScan
public class FetchCustomers implements CommandLineRunner {

private final ContractCustomerServiceImpl ccimpl;

    public FetchCustomers(ContractCustomerServiceImpl ccimpl) {
        this.ccimpl = ccimpl;
    }

    @Override
    public void run(String... args) throws Exception{
        System.out.println("Jahapp! Nu kör vi FetchCustomers!");

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        URL url = new URL("https://javaintegration.systementor.se/customers");
        AllCustomers allCustomers = xmlMapper.readValue(url, AllCustomers.class);

            for (ContractCustomer c : allCustomers.customers) {
                ccimpl.updateOrAddContractCustomer(c.getId(), ccimpl.contractCustomerToContractCustomerDTO(c));
            }
        }
    }


