package net.aurika.common.metalang.test;

import net.aurika.common.metalang.MetalangObject;
import net.aurika.common.metalang.flow.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Test {

  public static void main(String[] args) {
    try {
      MetalangObject metalangObject = FlowProcessor.flow(TestFlow.class.getAnnotation(FlowWrapper.class).value());
      System.out.println("metalang objet:");
      System.out.println(metalangObject);
    } catch (Throwable e) {
      e.printStackTrace(System.err);
    }
  }

}

@FlowWrapper(@Flow(
    variables = @Variables({
        "var_id", "var_name", "var_a"
    }),
    sets = {
        @VariableSet(
            name = "var_name",
            value = @Instance(
                type = Instance.Type.CONSTANT,
                constant = @Constant(
                    type = Constant.Type.STRING,
                    stringConst = "someName"
                )
            )
        ),
        @VariableSet(
            name = "var_id",
            value = @Instance(
                type = Instance.Type.VAR_GET,
                variableGet = @VariableGet("var_name")
            )
        )
    },
    constructs = {
        @Construct(
            find = @ConstructorFind(
                type = Instance1.class, params = @ExecutableParameters({String.class})
            ),
            args = @ExecutableArguments({
                @VariableGet("var_a")
            })
        )
    },
    mapping = {Flow.Action.SET, Flow.Action.CONSTRUCT, Flow.Action.SET}
))
class TestFlow { }

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface FlowWrapper {

  Flow value();

}

class Instance1 {

  public Instance1(String s) {
    System.out.println("Constructs " + Instance1.class + ": " + s);
  }

}
