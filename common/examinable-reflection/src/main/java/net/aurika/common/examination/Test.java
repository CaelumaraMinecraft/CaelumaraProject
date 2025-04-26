package net.aurika.common.examination;

import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.examination.reflection.ReflectionExaminableConstructor;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class Test {

  public static void main(String[] args) {
    TestExaminableSub test1sub = new TestExaminableSub(1, 4);
    TestExaminable test1 = new TestExaminable(11, 45, test1sub);

    System.out.println(StringExaminer.simpleEscaping().examine(test1));


    ExaminableConstructorRegistry registry = new DefaultExaminableConstructorRegistry();

    try {
      Constructor<TestExaminable> testConstructor = TestExaminable.class.getDeclaredConstructor(
          int.class, int.class, TestExaminableSub.class);

      ReflectionExaminableConstructor<TestExaminable> testCtr = ReflectionExaminableConstructor.create(
          TestExaminable.class, testConstructor);

      TestExaminable testReCtd1 = testCtr.construct(test1.examinableProperties());

      System.out.println(StringExaminer.simpleEscaping().examine(testReCtd1));

      testConstructor.setAccessible(true);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }


    try {
      Method testDataMtd = TestData1.class.getDeclaredMethod("create", int.class, byte.class, String.class);
      testDataMtd.setAccessible(true);

      ReflectionExaminableConstructor<TestData1> testDataCtr = ReflectionExaminableConstructor.create(
          TestData1.class, testDataMtd
      );

      Stream<? extends ExaminableProperty> stream = Stream.of(
          ExaminableProperty.of("c1", 0),
          ExaminableProperty.of("c2", ((byte) 0)),
          ExaminableProperty.of("c3", "A=========")
      );

      TestData1 testData1ReCtd = testDataCtr.construct(stream);

      System.out.println(StringExaminer.simpleEscaping().examine(testData1ReCtd));

    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

}

class TestExaminable implements Examinable {

  private final int a, b;
  private final TestExaminableSub sub;

  @ExaminableConstructor(publicType = TestExaminable.class, properties = {"a", "b", "sub"})
  public TestExaminable(int a, int b, TestExaminableSub sub) {
    this.a = a;
    this.b = b;
    this.sub = sub;
  }

  @Override
  public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("a", a),    //
        ExaminableProperty.of("b", b),    //
        ExaminableProperty.of("sub", sub) //
    );
  }

}

class TestExaminableSub implements Examinable {

  private final int c, d;

  @ExaminableConstructor(publicType = TestExaminableSub.class, properties = {"c", "d"})
  TestExaminableSub(int c, int d) {
    this.c = c;
    this.d = d;
  }

  @Override
  public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("c", c),  //
        ExaminableProperty.of("d", d)   //
    );
  }

}

class TestData1 implements Examinable {

  private final int c1;
  private final byte c2;
  private final String c3;

  @ExaminableConstructor(publicType = TestData1.class, properties = {"c1", "c2", "c3"})
  public static @NotNull TestData1 create(int c1, byte c2, String c3) {
    return new TestData1(c1, c2, c3);
  }

  private TestData1(int c1, byte c2, String c3) {
    this.c1 = c1;
    this.c2 = c2;
    this.c3 = c3;
  }

  @Override
  public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("c1", c1),
        ExaminableProperty.of("c2", c2),
        ExaminableProperty.of("c3", c3)
    );
  }

}
