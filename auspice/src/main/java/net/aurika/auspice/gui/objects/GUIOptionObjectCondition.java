package net.aurika.auspice.gui.objects;

import java.util.Objects;

public class GUIOptionObjectCondition {

  protected final ConditionCompiler.LogicalOperand condition;
  protected final GUIOptionBuilder option;

  public final GUIOptionBuilder getObject() {
    return this.option;
  }

  GUIOptionObjectCondition(ConditionCompiler.LogicalOperand var1, GUIOptionBuilder var2) {
    this.condition = var1;
    this.option = (GUIOptionBuilder) Objects.requireNonNull(var2, "Option cannot be null");
  }

}