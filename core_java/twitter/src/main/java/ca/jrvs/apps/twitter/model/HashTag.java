package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text",
    "indices"
})

public class HashTag {

  @JsonProperty("text")
  private String hashTagText;
  @JsonProperty("indices")
  private int[] indexArray;

  public String getHashTagText() {
    return hashTagText;
  }

  public void setHashTagText(String hashTagText) {
    this.hashTagText = hashTagText;
  }

  public int[] getIndexArray() {
    return indexArray;
  }

  public void setIndexArray(int[] indexArray) {
    this.indexArray = indexArray;
  }

}
