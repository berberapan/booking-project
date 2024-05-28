package org.example.booking_project;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.booking_project.configs.ContractCustomersProperties;
import org.example.booking_project.models.AllCustomers;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.service.impl.ContractCustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import java.net.URL;

@ComponentScan
public class FetchCustomers implements CommandLineRunner {

private final ContractCustomerServiceImpl ccimpl;
    private static final Logger log = LoggerFactory.getLogger(FetchCustomers.class);

    public FetchCustomers(ContractCustomerServiceImpl ccimpl) {
        this.ccimpl = ccimpl;
    }

    @Override
    public void run(String... args) throws Exception{
        log.info("Fetching client customers");

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        String fetchURL = ccimpl.getXmlStreamProvider().getProperties().contractCustomers.fetchurl;

        URL url = new URL(fetchURL);
        AllCustomers allCustomers = xmlMapper.readValue(url, AllCustomers.class);

            for (ContractCustomer c : allCustomers.customers) {
                ccimpl.updateOrAddContractCustomer(c.getId(), ccimpl.contractCustomerToContractCustomerDTO(c));
            }
        }
    }


