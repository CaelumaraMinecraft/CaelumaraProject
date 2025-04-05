package net.aurika.common.key.test;

import net.aurika.common.key.Key;

public class KeyTest {

  public static void main(String[] args) {

    try {
      Key testKey01 = Key.key("net.aurika.common.key.test:ss_dsd//sd");
      Key testKey02 = Key.key("net.aurika.common.key.test:success/sss");
      System.out.println(testKey01.toString());
      System.out.println(testKey01.asString());
      System.out.println(testKey02.toString());
      System.out.println(testKey02.asString());
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }

}
