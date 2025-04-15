package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.member.DyanasisMember;
import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public interface InstanceMemberHandler<O extends DyanasisObject, K, M extends DyanasisMember> {

  /**
   * Gets members for a dyanasis object, it doesn't contain the object extended members.
   *
   * @param object the dyanasis object
   * @return the members provided by this dyanasis type
   */
  @NotNull Map<K, ? extends M> members(@NotNull O object);

  @NotNull Map<K, ? extends M> protectedMembers(@NotNull O object);

  @NotNull Map<K, ? extends M> extendedMembers(@NotNull O object);

  /**
   * Registers a external member handler.
   *
   * @param key           the member key
   * @param memberHandler the member handler
   * @throws IllegalArgumentException when the key of member already existed
   */
  void registerExtended(@NotNull K key, @NotNull Function<O, M> memberHandler) throws IllegalArgumentException;

}