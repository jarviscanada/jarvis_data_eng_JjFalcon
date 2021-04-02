package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.HashTag;
import ca.jrvs.apps.twitter.model.UserMention;

public class EntityBuilder {

    public static Entities buildEntity(HashTag[] hashtags, UserMention[] mentions){
      Entities newEntity = new Entities();
      newEntity.setHashTags(hashtags);
      newEntity.setUserMentions(mentions);

      return newEntity;
    }

}
