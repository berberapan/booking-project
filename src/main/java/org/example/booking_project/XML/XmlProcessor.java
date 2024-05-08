package org.example.booking_project.XML;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.booking_project.models.AllCustomers;
import org.example.booking_project.models.ContractCustomer;
import org.example.booking_project.repos.ContractCustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.net.URL;

@Component
public class XmlProcessor implements CommandLineRunner {

    private final ContractCustomerRepo contractCustomerRepo;

    @Autowired
    public XmlProcessor(ContractCustomerRepo contractCustomerRepo) {
        this.contractCustomerRepo = contractCustomerRepo;
    }

    @Override
    public void run(String... args) throws Exception{
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        // Öppna en anslutning till URL:en och skapa en InputStream
        URL url = new URL("https://javaintegration.systementor.se/customers");
        AllCustomers allCustomers = xmlMapper.readValue(url, AllCustomers.class);

        for (ContractCustomer c : allCustomers.customers){
            System.out.println(c.getContactName());
        }
        contractCustomerRepo.saveAll(allCustomers.getCustomers());
    }
}


