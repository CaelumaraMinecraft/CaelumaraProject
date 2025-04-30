package net.aurika.commobn.nbt.test;

import net.aurika.common.nbt.NBTTag;
import net.aurika.common.nbt.NBTUtil;

public class NBTTest {

  public static void main(String[] args) {
    try {
      NBTTag tag1 = NBTUtil.tagFromSNBT("{\"key\":\"value\"}");
      System.out.println(tag1);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

}
