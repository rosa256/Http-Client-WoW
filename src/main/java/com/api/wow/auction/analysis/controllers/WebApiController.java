package com.api.wow.auction.analysis.controllers;

import com.api.wow.auction.analysis.WowApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RestController
public class WebApiController {

    private WowApiClient client;

    @GetMapping("/realms")
    public String getRealms() throws IOException, InterruptedException {
        return client.getRealms();
    }

    @GetMapping("/auctions/{connectedRealmId}")
    public String getAuctions(@PathVariable(name = "connectedRealmId") String connectedRealmId) throws IOException, InterruptedException {
        return client.getAuction(connectedRealmId);
    }

    @PostMapping("/token")
    public String getAccessToken() throws IOException, InterruptedException {
        return client.getToken();
    }
}
