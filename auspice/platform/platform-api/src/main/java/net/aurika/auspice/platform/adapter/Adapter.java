package net.aurika.auspice.platform.adapter;

import org.jetbrains.annotations.NotNull;

/**
 * An adapter.
 *
 * @param <AT> the auspice object type
 * @param <PT> the actual platform object type
 */
public interface Adapter<AT, PT> {

  @NotNull AT adaptToAuspice(@NotNull PT platformObj);

  @NotNull PT adaptToActual(@NotNull AT auspiceObj);

  @NotNull Class<? extends AT> auspiceType();

  @NotNull Class<? extends PT> platformType();

}
