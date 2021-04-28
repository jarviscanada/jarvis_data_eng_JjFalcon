package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created_at",
    "id",
    "id_str",
    "text",
    "entities",
    "coordinates",
    "retweet_count",
    "favorite_count",
    "favorited",
    "retweeted"
})

public class Tweet {

  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("id")
  private long id;
  @JsonProperty("id_str")
  private String idString;
  @JsonProperty("text")
  private String tweeterMessage;
  @JsonProperty("entities")
  private Entities entity;
  @JsonProperty("coordinates")
  private Coordinates location;
  @JsonProperty("retweet_count")
  private int retweetCount;
  @JsonProperty("favorite_count")
  private int favoriteCount;
  @JsonProperty("favorited")
  private boolean isFavorited;
  @JsonProperty("retweeted")
  private boolean isretweeted;

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
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

  public String getTweeterMessage() {
    return tweeterMessage;
  }

  public void setTweeterMessage(String tweeterMessage) {
    this.tweeterMessage = tweeterMessage;
  }

  public Entities getEntity() {
    return entity;
  }

  public void setEntity(Entities entity) {
    this.entity = entity;
  }

  public Coordinates getLocation() {
    return location;
  }

  public void setLocation(Coordinates location) {
    this.location = location;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  public int getFavoriteCount() {
    return favoriteCount;
  }

  public void setFavoriteCount(int favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  public boolean isFavorited() {
    return isFavorited;
  }

  public void setFavorited(boolean favorited) {
    isFavorited = favorited;
  }

  public boolean isIsretweeted() {
    return isretweeted;
  }

  public void setIsretweeted(boolean isretweeted) {
    this.isretweeted = isretweeted;
  }
}
