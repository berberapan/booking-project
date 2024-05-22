package org.example.booking_project.service.impl;

import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.configs.ShippersProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class JsonStreamProvider {

    @Autowired
    ShippersProperties properties;
    public InputStream getDataStream() throws IOException {

        String fetchURL = properties.getFetchurl();

        URL url = new URL(fetchURL);
        return url.openStream();
    }
}


