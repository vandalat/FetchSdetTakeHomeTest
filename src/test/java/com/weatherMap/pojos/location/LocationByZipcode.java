
package com.weatherMap.pojos.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "zip",
    "name",
    "lat",
    "lon",
    "country"
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationByZipcode {

    @JsonProperty("zip")
    public String zip;
    @JsonProperty("name")
    public String name;
    @JsonProperty("lat")
    public Float lat;
    @JsonProperty("lon")
    public Float lon;
    @JsonProperty("country")
    public String country;

}
