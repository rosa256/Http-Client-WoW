package com.api.wow.auction.analysis.pojos.auction;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "item",
        "buyout",
        "quantity",
        "time_left"
})
public class Auction {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("item")
    public Item item;
    @JsonProperty("buyout")
    public String buyout;
    @JsonProperty("quantity")
    public Integer quantity;
    @JsonProperty("time_left")
    public String timeLeft;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}