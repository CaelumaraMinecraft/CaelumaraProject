package net.aurika.auspice.gui;

import net.aurika.configuration.context.ConfigParsingContext;

public abstract class GUIOptionTemplate {

  public abstract GUIOptionObject build(ConfigParsingContext context);

}
