package net.aurika.common.event.test.cl;

public interface Super {

  public static String string() { return ""; }

}

interface Sub extends Super {

  public static String string() { return ""; }

}

class SubSub implements Sub {

  public static String string() { return ""; }

}

class SubSubSub extends SubSub {

  public static String string() { return Super.string(); }

}
