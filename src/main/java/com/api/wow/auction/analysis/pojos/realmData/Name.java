package com.api.wow.auction.analysis.pojos.realmData;
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
        "en_US",
        "es_MX",
        "pt_BR",
        "de_DE",
        "en_GB",
        "es_ES",
        "fr_FR",
        "it_IT",
        "ru_RU",
        "ko_KR",
        "zh_TW",
        "zh_CN"
})
public class Name {

    @JsonProperty("en_US")
    public String enUS;
    @JsonProperty("es_MX")
    public String esMX;
    @JsonProperty("pt_BR")
    public String ptBR;
    @JsonProperty("de_DE")
    public String deDE;
    @JsonProperty("en_GB")
    public String enGB;
    @JsonProperty("es_ES")
    public String esES;
    @JsonProperty("fr_FR")
    public String frFR;
    @JsonProperty("it_IT")
    public String itIT;
    @JsonProperty("ru_RU")
    public String ruRU;
    @JsonProperty("ko_KR")
    public String koKR;
    @JsonProperty("zh_TW")
    public String zhTW;
    @JsonProperty("zh_CN")
    public String zhCN;
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