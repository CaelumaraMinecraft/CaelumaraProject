package net.aurika.configuration.translator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ListTranslator<T> extends Translator<List<T>> {

  ListTranslator<Integer> INTEGER_LIST = new ListTranslator<Integer>() {
    @Override
    public List<Integer> translate(String string, Object... settings) {
      List<Integer> list = new ArrayList<>();                                          //TODO
      if (string.startsWith("[") && string.endsWith("]")) {
        string = string.substring(1, string.length() - 1);
      }
      return list;
    }
  };

  ListTranslator<String> STRING_LIST = new ListTranslator<String>() {

    @Override
    public List<String> translate(String string, Object[] settings) {  //TODO
      List<String> list = new ArrayList<String>();
      if (string.startsWith("[") && string.endsWith("]")) {
        string = string.substring(1, string.length() - 1);
      }
      return list;
    }
  };

  @NotNull
  @Override
  default Class<List> getOutputType() {
    return List.class;
  }

}
