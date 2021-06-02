package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.Map;

public class Maps {

  // https://howtodoinjava.com/java/collections/hashmap/compare-two-hashmaps/
  // comparing Entry
  public <K,V> boolean areMapsEqualLib (Map<K,V> map1, Map<K,V> map2){
    return map1.equals(map2) ? true : false;

  }

  // https://www.baeldung.com/java-compare-hashmaps#:~:text=re%20using%20Map.-,equals()%20to%20check%20if%20two%20HashMaps%20have%20the%20same,equals()%20method.
  // Compare Keys and Values
  public static <K, V> boolean areMapsEqual(Map<K, V> m1, Map<K, V> m2) {
    if (m1.keySet().size() != m2.keySet().size()) {
      return false;
    }

    // Value set of map1
    // HashSet<String> set1 = new HashSet<>(m1.values());

    // Value set of map2
    // HashSet<String> set2 = new HashSet<>(m2.values());

    // if (!m1.keySet().equals(m2.keySet()) || set1.equals(set2)) return false;

//    for (K key : m1.keySet()) {
//      if (!m2.containsKey(key) || !m1.get(key).equals(m2.get(key))) {
//        return false;
//      }
//    }

    return true;
  }

}
