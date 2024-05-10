package org.example.booking_project.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.booking_project.Dtos.BlacklistedDTO;
import org.example.booking_project.service.BlacklistService;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BlacklistedServiceImpl implements BlacklistService {
    @Override
    public void updateBlacklisted(BlacklistedDTO blacklistedDTO) throws IOException {
        if (blacklistedDTO.isOk()) {
            blacklistedDTO.setOk(false);
        } else {
            blacklistedDTO.setOk(true);
        }

        HttpClient client = HttpClient.newHttpClient();

        if (!existsByEmail(blacklistedDTO.email)) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://javabl.systementor.se/api/gul/blacklist"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers
                            .ofString("{\"email\":\"" + blacklistedDTO.email + "\", " +
                                    "\"name\":\"" + blacklistedDTO.name + "\", " +
                                    "\"ok\":\"" + blacklistedDTO.ok + "\" }"))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        } else {
            System.out.println("https://javabl.systementor.se/api/gul/blacklist/" + blacklistedDTO.getEmail().trim());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://javabl.systementor.se/api/gul/blacklist/" + blacklistedDTO.getEmail().trim()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers
                            .ofString("{\"name\":\"" + blacklistedDTO.getName() + "\", " +
                                    "\"ok\":\"" + blacklistedDTO.isOk() + "\" }"))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        }

    }

    @Override
    public BlacklistedDTO getBlacklistedByEmail(String email) throws IOException {

        BlacklistedDTO[] allBlacklisted = getBlacklistedArrayFromSource();

        for (BlacklistedDTO b : allBlacklisted) {
            if (b.email != null && b.email.equals(email.trim())) {
                return b;
            }
        }
        return null;
    }

    @Override
    public boolean existsByEmail(String email) throws IOException {
        BlacklistedDTO[] allBlacklisted = getBlacklistedArrayFromSource();

        for (BlacklistedDTO b : allBlacklisted) {
            if (b.email != null && b.email.equals(email.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BlacklistedDTO[] getBlacklistedArrayFromSource() throws IOException {
        JsonMapper jm = new JsonMapper();

        return jm.readValue(new URL("https://javabl.systementor.se/api/gul/blacklist"),
                BlacklistedDTO[].class);
    }
}
