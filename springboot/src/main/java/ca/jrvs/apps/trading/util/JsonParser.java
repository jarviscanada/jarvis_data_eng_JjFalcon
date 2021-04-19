package ca.jrvs.apps.trading.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  public static String toJason(Object object, boolean prettyJson, boolean includeNullValues ) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    if (!includeNullValues) {
      mapper.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson){
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return mapper.writeValueAsString(object);
  }

  public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ObjectReader reader = mapper.reader().withRootName("quote");
    // return (T) mapper.readValue(json, clazz);
    return (T) reader.forType(clazz).readValue(json);
  }

}
