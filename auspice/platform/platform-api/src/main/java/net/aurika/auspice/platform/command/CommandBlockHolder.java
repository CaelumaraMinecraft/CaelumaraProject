package net.aurika.auspice.platform.command;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

// TODO rename

/**
 * Represents a command block like. It can hold commands, lastOutputs and successCount.
 */
public interface CommandBlockHolder {

  /**
   * Gets the command that this CommandBlock will run when powered.
   * This will never return null.  If the CommandBlock does not have a
   * command, an empty String will be returned instead.
   *
   * @return Command that this CommandBlock will run when activated.
   */
  String command();

  /**
   * Sets the command that this CommandBlock will run when powered.
   * Setting the command to null is the same as setting it to an empty
   * String.
   *
   * @param command Command that this CommandBlock will run when activated.
   */
  void command(@Nullable String command);

  /**
   * Gets the last output from this command block.
   *
   * @return the last output
   */
  @Nullable Component lastOutput();

  /**
   * Sets the last output from this command block.
   *
   * @param lastOutput the last output
   */
  void lastOutput(@Nullable Component lastOutput);

  /**
   * Gets the success count from this command block.
   *
   * @return the success count
   * @see <a href="https://minecraft.wiki/wiki/Command_Block#Success_count">Command_Block#Success_count</a>
   */
  int successCount();

  /**
   * Sets the success count from this command block.
   *
   * @param successCount the success count
   * @see <a href="https://minecraft.wiki/wiki/Command_Block#Success_count">Command_Block#Success_count</a>
   */
  void successCount(int successCount);

}
