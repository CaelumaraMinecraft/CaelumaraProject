package net.aurika.dyanasis.api.compiler.setting;

import org.jetbrains.annotations.NotNull;

public interface DyanasisCompilerSetting {

  Object get(@NotNull DyanasisCompilerSettingKey key);

  interface Builder {

    @NotNull DyanasisCompilerSetting build();

  }

}
