package org.example.booking_project.service.impl;

import lombok.Getter;
import org.example.booking_project.configs.ContractCustomersProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
@Getter
public class XmlStreamProvider {

    @Autowired
    ContractCustomersProperties properties;
    public InputStream getDataStream() throws IOException {

        String fetchURL = properties.getFetchurl();

        URL url = new URL(fetchURL);
        return url.openStream();
    }
}


