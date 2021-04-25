package com.api.wow.auction.analysis.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
//  private final LoginClient loginClient;

  public String getAuthenticationToken() throws IOException {

    String[] credentials = getClientCredentialsFromFile();

    Map<String, String> map = new HashMap<>();
    map.put("client_id",credentials[0]);
    map.put("client_secret",credentials[1]);
    map.put("grant_type","client_credentials");

    return "loginClient.getToken(map).getAccessToken()";
  }

  private String[] getClientCredentialsFromFile() throws IOException {
    System.out.println("READING FILE");
    InputStream inputStream = new FileInputStream(new File("client_credentials_file.txt"));
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String[] clientCredentials = new String[2];
    clientCredentials[0] = reader.readLine().split(":")[1]; // First line - client ID
    clientCredentials[1] = reader.readLine().split(":")[1]; // Second line - client Secret
    return clientCredentials;
  }

}
