package com.api.wow.auction.analysis.pojos.auction;


import java.util.HashMap;
import java.util.List;
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
        "context",
        "modifiers",
        "pet_breed_id",
        "pet_level",
        "pet_quality_id",
        "pet_species_id"
})
public class Item {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("context")
    public Integer context;
    @JsonProperty("modifiers")
    public List<Modifier> modifiers = null;
    @JsonProperty("pet_breed_id")
    public Integer petBreedId;
    @JsonProperty("pet_level")
    public Integer petLevel;
    @JsonProperty("pet_quality_id")
    public Integer petQualityId;
    @JsonProperty("pet_species_id")
    public Integer petSpeciesId;
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