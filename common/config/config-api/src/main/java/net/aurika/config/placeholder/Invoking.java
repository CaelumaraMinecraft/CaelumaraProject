package net.aurika.config.placeholder;

public abstract class Invoking {

  public static final class AttributeInvoking extends Invoking {

    private final String attributeName;

    public AttributeInvoking(String attributeName) {
      this.attributeName = attributeName;
    }

  }

  public static final class FunctionInvoking extends FunctionalFormInvoking {

    public FunctionInvoking(String functionName, String arguments) {
      super(functionName, arguments);
    }

  }

  public static final class MacroInvoking extends FunctionalFormInvoking {

    public MacroInvoking(String functionName, String arguments) {
      super(functionName, arguments);
    }

  }

  public static abstract class FunctionalFormInvoking extends Invoking {

    protected final String functionName;
    protected final String arguments;

    protected FunctionalFormInvoking(String functionName, String arguments) {
      this.functionName = functionName;
      this.arguments = arguments;
    }

  }

}
