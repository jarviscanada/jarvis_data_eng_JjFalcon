package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "coordinates",
    "type"
})

public class Coordinates {

  @JsonProperty("coordinates")
  private Double[] coordinates;
  @JsonProperty("type")
  private String type;

  public Double[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Double[] coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getLatitude() { return coordinates[0];}
  public Double getLongitude() { return coordinates[1];}

}
