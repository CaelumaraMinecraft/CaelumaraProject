package net.aurika.dyanasis.api.declaration.doc;

import org.jetbrains.annotations.Nullable;

public interface DyanasisDocEditable<Doc extends DyanasisDoc> {

  /**
   * Edits the dyanasis doc.
   *
   * @param doc the doc
   */
  void dyanasisDoc(@Nullable Doc doc);

}
