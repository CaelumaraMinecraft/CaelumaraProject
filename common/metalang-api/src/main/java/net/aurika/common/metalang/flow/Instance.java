package net.aurika.common.metalang.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static net.aurika.common.metalang.MetalangObject.VAR_NULL;
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Instance {

  Type type();

  Constant constant() default @Constant(type = Constant.Type.INT);

  VariableGet variableGet() default @VariableGet(value = "");

  Invoke invoke() default @Invoke(
      find = @MethodFind(
          name = "<>",
          params = @ExecutableParameters({}),
          inClass = void.class
      ),
      args = @ExecutableArguments({}),
      isStatic = true,
      obj = @VariableGet(value = VAR_NULL)
  );

  Construct construct() default @Construct(
      find = @ConstructorFind(
          type = void.class,
          params = @ExecutableParameters({})
      ),
      args = @ExecutableArguments({})
  );


  enum Type {
    CONSTANT, VAR_GET, INVOKE, CONSTRUCT
  }

}
