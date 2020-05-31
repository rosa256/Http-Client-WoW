package com.api.wow.auction.analysis.pojos.accessToken;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class AccessToken {

    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expires_in")
    public Integer expiresIn;

    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
