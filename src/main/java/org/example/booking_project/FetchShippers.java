package org.example.booking_project;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan
public class FetchShippers implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Jahapp! Nu kör vi FetchShippers!");
        JacksonXmlModule module = new JacksonXmlModule();
////        module.setDefaultUseWrapper(false);
////        XmlMapper xmlMapper = new XmlMapper(module);
////        catalog theBooks = xmlMapper.readValue(new URL("https://axmjqhyyjpat.objectstorage.eu-amsterdam-1.oci.customer-oci.com/n/axmjqhyyjpat/b/aspcodeprod/o/books.xml"),
////                catalog.class
////        );
    }
/* ObjectMapper om = new ObjectMapper();
Root[] root = om.readValue(myJsonString, Root[].class); */
    //https://javaintegration.systementor.se/shippers
}
