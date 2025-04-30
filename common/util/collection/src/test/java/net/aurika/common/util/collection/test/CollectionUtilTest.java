package net.aurika.common.util.collection.test;

import net.aurika.util.collection.IndexedMap;
import net.aurika.util.collection.WrapperIndexedMap;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionUtilTest {

  public static void main(String[] args) {
    try {
      IndexedMap<String, Integer> map = new WrapperIndexedMap<>(new ArrayList<>(), new HashMap<>());
      map.put("a", 1);
      map.put("b", 2);
      map.put("c", 3);
      map.insert(0, "abc", 8);
      System.out.println(map);
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

}
