package net.aurika.common.metalang.flow;

import net.aurika.common.metalang.MetalangObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FlowProcessor {

  public static @NotNull MetalangObject flow(@NotNull Flow flow) {
    Variables variables = flow.variables();
    MetalangObject metalangObject = MetalangObject.metalangObject(variables);
    acceptFlow(metalangObject, flow);
    return metalangObject;
  }

  public static void acceptFlow(@NotNull MetalangObject metalangObject, @NotNull Flow flow) {
    acceptFlow(metalangObject, mappingFlow(flow));
  }

  public static Annotation @NotNull [] mappingFlow(@NotNull Flow flow) {
    VariableGet[] variableGets = flow.gets();
    int variableGetsCount = variableGets.length;
    int variableGetsIndex = 0;

    VariableSet[] variableSets = flow.sets();
    int variableSetsCount = variableSets.length;
    int variableSetsIndex = 0;

    Invoke[] invokes = flow.invokes();
    int invokesCount = invokes.length;
    int invokesIndex = 0;

    Construct[] construct = flow.constructs();
    int constructsCount = construct.length;
    int constructsIndex = 0;

    int actionsCount = variableGetsCount + variableSetsCount + invokesCount + constructsCount;

    Flow.Action[] mapping = flow.mapping();

    if (mapping.length != actionsCount) {
      throw new IllegalArgumentException("actionsCount != mappingLength");
    }

    Annotation[] mappedFlow = new Annotation[actionsCount];
    for (int i = 0; i < actionsCount; i++) {
      Flow.Action action = mapping[i];
      switch (action) {
        case GET:
          mappedFlow[i] = variableGets[variableGetsIndex++];
          continue;
        case SET:
          mappedFlow[i] = variableSets[variableSetsIndex++];
          continue;
        case INVOKE:
          mappedFlow[i] = invokes[invokesIndex++];
          continue;
        case CONSTRUCT:
          mappedFlow[i] = construct[constructsIndex++];
          continue;
        default:
          continue;
      }
    }
    return mappedFlow;
  }

  protected static void acceptFlow(@NotNull MetalangObject metalangObject, Annotation @NotNull [] actions) {
    for (Annotation actionAnn : actions) {
      if (actionAnn instanceof VariableSet) {
        VariableSet variableSet = (VariableSet) actionAnn;
        metalangObject.setVariable(variableSet.name(), getInstance(metalangObject, variableSet.value()));
      }
      if (actionAnn instanceof Invoke) {
        Invoke invoke = (Invoke) actionAnn;
        invoke(metalangObject, invoke);
      }
      if (actionAnn instanceof Construct) {
        Construct construct = (Construct) actionAnn;
        construct(metalangObject, construct);
      }
    }
  }

  public static @Nullable Constructor<?> findConstructor(@NotNull ConstructorFind constructorFind) {
    try {
      return constructorFind.type().getConstructor(constructorFind.params().value());
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  public static @Nullable Method findMethod(@NotNull MethodFind methodFind) {
    String methodName = methodFind.name();
    Class<?>[] parameters = methodFind.params().value();
    Class<?> inClass = methodFind.inClass();
    try {
      return inClass.getMethod(methodName, parameters);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  public static @Nullable Object invoke(@NotNull MetalangObject metalangObject, @NotNull Invoke invoke) {
    MethodFind methodFind = invoke.find();
    @Nullable Method foundMethod = findMethod(methodFind);
    boolean isStatic = invoke.isStatic();
    if (foundMethod == null) {
      return null;
    } else {
      Object[] args = getArguments(metalangObject, invoke.args());
      try {
        if (isStatic) {
          foundMethod.setAccessible(true);
          return foundMethod.invoke(null, args);
        } else {
          foundMethod.setAccessible(false);
          return foundMethod.invoke(getVariable(metalangObject, invoke.obj()), args);
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static @NotNull Object construct(@NotNull MetalangObject metalangObject, @NotNull Construct construct) {
    ConstructorFind constructorFind = construct.find();
    @Nullable Constructor<?> foundConstructor = findConstructor(constructorFind);
    if (foundConstructor == null) {
      throw new IllegalArgumentException("Cannot find constructor: " + Arrays.toString(
          constructorFind.params().value()));
    } else {
      Object[] args = getArguments(metalangObject, construct.args());
      try {
        foundConstructor.setAccessible(true);
        return foundConstructor.newInstance(args);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static Object @NotNull [] getArguments(@NotNull MetalangObject metalangObject, @NotNull ExecutableArguments executableArguments) {
    VariableGet[] args = executableArguments.value();
    int len = args.length;
    Object[] result = new Object[len];
    for (int i = 0; i < len; i++) {
      result[i] = getVariable(metalangObject, args[i]);
    }
    return result;
  }

  public static Object getVariable(@NotNull MetalangObject metalangObject, @NotNull VariableGet variableGet) {
    return metalangObject.getVariable(variableGet.value());
  }

  public static @Nullable Object getInstance(@NotNull MetalangObject metalangObject, @NotNull Instance instance) {
    Instance.Type type = instance.type();
    switch (type) {
      case CONSTANT:
        return getConstant(instance.constant());
      case VAR_GET:
        return metalangObject.getVariable(instance.variableGet().value());
      case INVOKE:
        return invoke(metalangObject, instance.invoke());
      case CONSTRUCT:
        return construct(metalangObject, instance.construct());
      default:
        throw new UnsupportedOperationException("Unsupported instance expression type: " + type);
    }
  }

  @SuppressWarnings("rawtypes")
  public static @Nullable Object getConstant(@NotNull Constant constant) {
    Constant.Type type = constant.type();
    switch (type) {
      case NULL:
        return null;
      case BOOLEAN:
        return constant.booleanConst();
      case CHAR:
        return constant.charConst();
      case BYTE:
        return constant.byteConst();
      case SHORT:
        return constant.shortConst();
      case INT:
        return constant.intConst();
      case LONG:
        return constant.longConst();
      case FLOAT:
        return constant.floatConst();
      case DOUBLE:
        return constant.doubleConst();
      case STRING:
        return constant.stringConst();
      case ENUM:
        Class<? extends Enum> enumClass = constant.enumConstClass();
        if (enumClass == Enum.class) {
          throw new IllegalArgumentException("Enum constant class cannot be " + Enum.class);
        }
        String enumName = constant.enumConstName();
        for (Enum e : enumClass.getEnumConstants()) {
          if (e.name().equals(enumName)) {
            return e;
          }
        }
        throw new IllegalArgumentException("Enum constant in enu, " + enumClass + " does not exist");
      case CLASS:
        return constant.classConst();
      default:
        throw new IllegalArgumentException("Unsupported constant type " + type);
    }
  }

}
