package com.api.wow.auction.analysis;

import com.api.wow.auction.analysis.models.AccessTokenData;
import com.api.wow.auction.analysis.models.AuthenticationData;
import com.api.wow.auction.analysis.pojos.accessToken.AccessToken;
import com.api.wow.auction.analysis.pojos.auction.AuctionData;
import com.api.wow.auction.analysis.pojos.realmData.RealmData;
import com.api.wow.auction.analysis.repositories.AuthenticationRepository;
import com.api.wow.auction.analysis.repositories.WowApiTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WowApiClient {

    private AuthenticationRepository authRepository;
    private WowApiTokenRepository accessTokenRepository;

    @Autowired
    public WowApiClient(AuthenticationRepository authenticationRepository, WowApiTokenRepository wowApiTokenRepository){
        this.authRepository = authenticationRepository;
        this.accessTokenRepository = wowApiTokenRepository;
    }

    private HttpClient httpClient = HttpClient.newHttpClient();
    private static final String REALM_URI = "https://eu.api.blizzard.com/data/wow/realm/index";
    private static final String TOKEN_URI = "https://eu.battle.net/oauth/token";

    public String getToken() throws IOException, InterruptedException {

        Map<Object, Object> credentialsDataMap = new HashMap<>();
        //TODO:START Getting Recent Record
        AuthenticationData authenticationData = authRepository.findById(1).get();
        //TODO:END
        credentialsDataMap.put("client_id", authenticationData.getClientId());
        credentialsDataMap.put("client_secret", authenticationData.getClientSecret());
        credentialsDataMap.put("grant_type", "client_credentials");

        HttpRequest requestToken = HttpRequest.newBuilder()
                .POST(ofFormEncodedData(credentialsDataMap))
                .setHeader("accept", "application/json")
                .setHeader("content-type", "application/x-www-form-urlencoded")
                .uri(URI.create(TOKEN_URI))
                .build();

        HttpResponse<String> response = httpClient.send(requestToken, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        AccessToken accessToken = objectMapper.readValue(response.body(), AccessToken.class);

        //TODO Start Mapping AccessToken to AccessTokenData
        AccessTokenData accessTokenData = new AccessTokenData();
        accessTokenData.setAccessToken(accessToken.getAccessToken());

        //TODO: Time add 2 hours.
        accessTokenData.setLastModified(new Timestamp(new Date().getTime()));
        //TODO End

        accessTokenRepository.save(accessTokenData);
        return accessToken.toString();
    }

    private static HttpRequest.BodyPublisher ofFormEncodedData(Map<Object, Object> data) {
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

        AccessTokenData accessTokenData = accessTokenRepository.findById(1).get();
        HttpRequest requestRealms = HttpRequest.newBuilder()
            .GET()
                .setHeader("accept", "application/json")
                .setHeader("Battlenet-namespace","dynamic-eu")
                .setHeader("Authorization", "Bearer " + accessTokenData.getAccessToken())
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

    return "<b> Joined Realms: </b> <br>" + joinedRealms;
    }

    public String getAuction(String connectedRealmId) throws IOException, InterruptedException {
        String AUCTION_URI = "https://eu.api.blizzard.com/data/wow/connected-realm/"+ connectedRealmId +"/auctions";

        AccessTokenData accessTokenData = accessTokenRepository.findById(1).get();

        HttpRequest requestAuctions = HttpRequest.newBuilder()
                .GET()
                .setHeader("accept", "application/json")
                .setHeader("Battlenet-namespace","dynamic-eu")
                .setHeader("Authorization", "Bearer " + accessTokenData.getAccessToken())
                .uri(URI.create(AUCTION_URI))
                .build();


        HttpResponse<String> response = httpClient.send(requestAuctions, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapperStringToJson = new ObjectMapper();
        AuctionData auctionData = mapperStringToJson.readValue(response.body(), AuctionData.class);

        String joinedAuctions = auctionData.getAuctions().stream()
                .map(auction -> "Item id: " + auction.item.id + "Quantity: " + auction.quantity + " Buyout: " + auction.buyout)
                .collect(Collectors.joining("<br>"));

        return "<b>Items</b><br> "+ joinedAuctions;
    }
}