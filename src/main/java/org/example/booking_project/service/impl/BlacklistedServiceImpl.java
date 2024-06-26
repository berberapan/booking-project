package org.example.booking_project.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.booking_project.Dtos.BlacklistedDTO;
import org.example.booking_project.configs.IntegrationsProperties;
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

    IntegrationsProperties properties;
    JsonStreamProvider jsp;
    public BlacklistedServiceImpl(JsonStreamProvider jsp, IntegrationsProperties properties) {
        this.jsp = jsp;
        this.properties = properties;
    }

    @Override
    public void updateBlacklisted(BlacklistedDTO blacklistedDTO) throws IOException {
        String blacklistPostURL = properties.blacklist.postUrl;
        String blacklistPutURL = properties.blacklist.putUrl;

        blacklistedDTO.ok = !blacklistedDTO.ok;

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        if (!existsByEmail(blacklistedDTO.email)) {
            blacklistedDTO.setOk(false);
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("email", blacklistedDTO.getEmail());
            requestJson.put("name", blacklistedDTO.getName());
            requestJson.put("ok", blacklistedDTO.isOk());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(blacklistPostURL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        } else {
            System.out.println(blacklistedDTO.name);
            System.out.println("Allowed to book: " + blacklistedDTO.ok);

            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("name", blacklistedDTO.getName());
            requestJson.put("ok", blacklistedDTO.isOk());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(blacklistPutURL + blacklistedDTO.email.trim()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
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
        String blacklistFetchURL = properties.blacklist.fetchUrl;

        return jm.readValue(new URL(blacklistFetchURL),
                BlacklistedDTO[].class);
    }

    @Override
    public boolean checkIfCstBlacklisted(String email) throws IOException {
        BlacklistedDTO[] allBlacklisted = getBlacklistedArrayFromSource();

        if (email == null) {email = "";}

        for (BlacklistedDTO b : allBlacklisted) {
            if (b.email != null && b.email.equals(email.trim()) && !b.isOk()) {
                return true;
            }
        }
        return false;
    }
}
