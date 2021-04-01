package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "indices",
    "screen_name",
    "id",
    "id_str"
})

public class UserMention {

  @JsonProperty("name")
  private String name;
  @JsonProperty("indices")
  private int[] indexArray;
  @JsonProperty("screen_name")
  private String screenName;
  @JsonProperty("id")
  private long id;
  @JsonProperty("id_str")
  private String idString;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int[] getIndexArray() {
    return indexArray;
  }

  public void setIndexArray(int[] indexArray) {
    this.indexArray = indexArray;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getIdString() {
    return idString;
  }

  public void setIdString(String idString) {
    this.idString = idString;
  }

}
