package net.aurika.configuration.sections;

import java.util.List;

public interface CommentableConfigSection {

  List<String> getComments(CommentType type);

  void setComments(List<String> comments, CommentType type);

  enum CommentType {
    BLOCK,
    INLINE,
    END
  }

}
