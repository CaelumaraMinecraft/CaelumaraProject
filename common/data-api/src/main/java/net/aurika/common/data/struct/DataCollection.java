package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface DataCollection<E> extends Iterable<E> {

  int size();

  @NotNull E get(int index) throws IndexOutOfBoundsException;

  @Override
  @NotNull Iterator<E> iterator();

}
