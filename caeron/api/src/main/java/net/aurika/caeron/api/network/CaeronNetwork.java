package net.aurika.caeron.api.network;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * 标记一个 caeron 网络, 比如货运网络, 电力网络, 传送网络.
 */
public interface CaeronNetwork {

  /**
   * Gets the number of components this caeron network has.
   *
   * @return the components count
   */
  int componentsCount();

  @NotNull Set<? extends Component> components();

  /**
   * Caeron 网络的任意组件.
   */
  interface Component { }

}
