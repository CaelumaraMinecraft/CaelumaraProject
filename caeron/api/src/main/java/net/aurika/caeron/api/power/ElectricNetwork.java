package net.aurika.caeron.api.power;

import net.aurika.caeron.api.network.CaeronNetwork;

/**
 * A caeron electric network.
 */
public interface ElectricNetwork extends CaeronNetwork {

  /**
   * The electric network generator.
   */
  interface Generator extends Component { }

  /**
   * The electric network connector.
   */
  interface Connector extends Component { }

  /**
   * The electric network capacitor.
   */
  interface Capacitor extends Component { }

  /**
   * The electric network component.
   */
  interface Component { }

}
