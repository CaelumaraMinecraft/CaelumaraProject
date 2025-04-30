package net.aurika.auspice.platform.location.abstraction;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface Grid2Pos extends GridXAware, GridZAware, Examinable {

  @Override
  int gridX();

  @Override
  int gridZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_X, gridX()),  // x
        ExaminableProperty.of(VAL_Z, gridZ())   // z
    );
  }

}
