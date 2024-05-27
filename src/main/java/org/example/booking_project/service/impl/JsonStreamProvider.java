package org.example.booking_project.service.impl;

import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.configs.ShippersProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class JsonStreamProvider {

    @Autowired
    IntegrationsProperties properties;
    public InputStream getDataStream(String source) throws IOException {

        //String fetchURL = properties.shippers.fetchurl;

        URL url = new URL(source);
        return url.openStream();
    }
}


