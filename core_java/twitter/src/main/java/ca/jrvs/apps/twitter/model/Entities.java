package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hashtags",
    "user_mentions"
})

public class Entities {

  @JsonProperty("hashtags")
  private HashTag[] hashTags = {};
  @JsonProperty("user_mentions")
  private UserMention[] userMentions = {};

  public HashTag[] getHashTags() {
    return hashTags;
  }

  public void setHashTags(HashTag[] hashTags) {
    this.hashTags = hashTags;
  }

  public UserMention[] getUserMentions() {
    return userMentions;
  }

  public void setUserMentions(UserMention[] userMentions) {
    this.userMentions = userMentions;
  }
}
