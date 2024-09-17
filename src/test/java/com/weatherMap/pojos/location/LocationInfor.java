
package com.weatherMap.pojos.location;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "lat",
    "lon",
    "country",
    "state"
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationInfor {

    @JsonProperty("name")
    public String name;
    @JsonProperty("lat")
    public Float lat;
    @JsonProperty("lon")
    public Float lon;
    @JsonProperty("country")
    public String country;
    @JsonProperty("state")
    public String state;
    @JsonProperty("local_names")
    public Map<String, String> local_names;

}
