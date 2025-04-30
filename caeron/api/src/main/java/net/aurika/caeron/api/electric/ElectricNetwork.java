package net.aurika.caeron.api.electric;

import net.aurika.caeron.api.network.CaeronNetwork;
import net.aurika.caeron.api.network.TickedCaeronNetwork;
import net.aurika.common.keyed.annotation.KeyedBy;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * A caeron electric network.
 */
@KeyedBy(value = "electricNetworkId")
public interface ElectricNetwork extends TickedCaeronNetwork {

  String VAL_ELECTRICS = "electrics";
  String VAL_COMPONENTS = "components";

  int electricNetworkId();

  @Override
  void onCaeronTick(long ticks);

  /**
   * Gets the total electrics of this electric network.
   *
   * @return the total electrics
   */
  long electrics();

  /**
   * Gets the total capacity of the electric network.
   *
   * @return the total capacity
   */
  default long capacity() {
    long capacity = 0;
    for (Component component : components()) {
      if (component instanceof Capacitor) {
        Capacitor capacitor = (Capacitor) component;
        capacity += capacitor.capacity();
      }
    }
    return capacity;
  }

  int componentsCount();

  default @NotNull Set<? extends @NotNull Generator> generators() {
    return filterComponents(Generator.class);
  }

  default @NotNull Set<? extends @NotNull Connector> connectors() {
    return filterComponents(Connector.class);
  }

  default @NotNull Set<? extends @NotNull Capacitor> capacitors() {
    return filterComponents(Capacitor.class);
  }

  default <T> @NotNull Set<? extends @NotNull T> filterComponents(@NotNull Class<T> type) {
    Set<T> filtered = new HashSet<>();
    for (Component component : components()) {
      if (type.isInstance(component)) {
        // noinspection unchecked
        filtered.add((T) component);
      }
    }
    return filtered;
  }

  @Override
  @NotNull Set<? extends @NotNull Component> components();

  /**
   * The electric network generator.
   */
  interface Generator extends Component {

    long electricsPerTick();

  }

  /**
   * The electric network connector.
   */
  @ApiStatus.Experimental
  interface Connector extends Component { }

  /**
   * The electric network capacitor.
   */
  interface Capacitor extends Component {

    /**
     * Gets the capacity this capacitor provided.
     *
     * @return the capacity
     */
    long capacity();

  }

  /**
   * The electric network component.
   */
  interface Component extends CaeronNetwork.Component { }

}
