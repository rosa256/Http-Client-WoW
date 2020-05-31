package com.api.wow.auction.analysis;

import com.api.wow.auction.analysis.pojos.accessToken.AccessToken;
import com.api.wow.auction.analysis.pojos.auction.AuctionData;
import com.api.wow.auction.analysis.pojos.realmData.RealmData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WowApiClient {

    private HttpClient httpClient = HttpClient.newHttpClient();
    private static final String REALM_URI = "https://eu.api.blizzard.com/data/wow/realm/index";
    private static final String TOKEN_URI = "https://eu.battle.net/oauth/token";

    public String getToken() throws IOException, InterruptedException {

        Map<Object, Object> credentialsDataMap = new HashMap<>();
        //TODO:START
        credentialsDataMap.put("access_id", "<ID FROM DB>");
        credentialsDataMap.put("access_secret", "<SECRET FROM DB>");
        //TODO:END
        credentialsDataMap.put("grant_type", "client_credentials");

        HttpRequest requestToken = HttpRequest.newBuilder()
                .POST(ofFormData(credentialsDataMap))
                .setHeader("accept", "application/json")
                .setHeader("content-type", "application/x-www-form-urlencoded")
                .uri(URI.create(TOKEN_URI))
                .build();
l
        HttpResponse<String> response = httpClient.send(requestToken, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        AccessToken accessToken = objectMapper.readValue(response.body(), AccessToken.class);

        return accessToken.toString();
    }

    private static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    public String getRealms() throws IOException, InterruptedException {

        HttpRequest requestRealms = HttpRequest.newBuilder()
            .GET()
                .setHeader("accept", "application/json")
                .setHeader("Battlenet-namespace","dynamic-eu")
                .setHeader("Authorization", "Bearer <TOKEN FROM DB>")
                .setHeader("region", "eu")
            .uri(URI.create(REALM_URI))
            .build();

        HttpResponse<String> response = httpClient.send(requestRealms, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapperStringToJson = new ObjectMapper();
        RealmData realmData = mapperStringToJson.readValue(response.body(), RealmData.class);

        String joinedRealms = realmData.getRealms()
                                .stream()
                                .map(realm -> realm.name.enGB + ": " + realm.id)
                                .collect(Collectors.joining("<br>"));

        System.out.println(response.statusCode());
        System.out.println("Stopped");
    return "<b> Joined Realms: </b> <br>" + joinedRealms;
    }

    public String getAuction(String connectedRealmId) throws IOException, InterruptedException {
        String AUCTION_URI = "https://eu.api.blizzard.com/data/wow/connected-realm/"+ connectedRealmId +"/auctions";

        HttpRequest requestAuctions = HttpRequest.newBuilder()
                .GET()
                .setHeader("accept", "application/json")
                .setHeader("Battlenet-namespace","dynamic-eu")
                .setHeader("Authorization", "Bearer <TOKEN FROM DB>")
                .uri(URI.create(AUCTION_URI))
                .build();


        HttpResponse<String> response = httpClient.send(requestAuctions, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.request().uri().toString());
        System.out.println(response.request().headers());

        ObjectMapper mapperStringToJson = new ObjectMapper();
        AuctionData auctionData = mapperStringToJson.readValue(response.body(), AuctionData.class);

        String joinedAuctions = auctionData.getAuctions().stream()
                .map(auction -> "Item id: " + auction.item.id + "Quantity: " + auction.quantity + " Buyout: " + auction.buyout)
                .collect(Collectors.joining("<br>"));

        return "<b>Items</b><br> "+ joinedAuctions;
    }
}