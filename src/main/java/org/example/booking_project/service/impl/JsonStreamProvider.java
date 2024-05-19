package org.example.booking_project.service.impl;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class JsonStreamProvider {
    public InputStream getDataStream() throws IOException {
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        return url.openStream();
    }
}

