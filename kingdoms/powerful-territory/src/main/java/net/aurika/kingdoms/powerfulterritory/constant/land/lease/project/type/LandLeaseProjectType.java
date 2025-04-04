package net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.type;

import net.aurika.kingdoms.powerfulterritory.constant.land.lease.LandLease;
import net.aurika.validate.Validate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.abstraction.data.DeserializationContext;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.data.StaticDeserializable;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;

import java.util.Objects;

public abstract class LandLeaseProjectType<T extends LandLease> implements Namespaced, LandLeaseProjectTypeAware, StaticDeserializable<T> {

  private static int hashCount = 0;

  private static synchronized int hash() {
    return hashCount++;
  }

  private final @NotNull Namespace namespace;
  private final int hash = hash();

  public LandLeaseProjectType(@NotNull Namespace namespace) {
    Validate.Arg.notNull(namespace, "namespace");
    this.namespace = namespace;
  }

  @Override
  public abstract @NotNull T deserialize(@NotNull DeserializationContext<SectionableDataGetter> deserializationContext);

  @Override
  public final @NonNull Namespace getNamespace() {
    return namespace;
  }

  @Override
  public @NotNull LandLeaseProjectType<T> landLeaseType() {
    return this;
  }

  @Override
  public final int hashCode() {
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof LandLeaseProjectType<?> leaseType) {
      return Objects.equals(this.namespace, leaseType.namespace);
    }
    return false;
  }

  @Override
  public @NotNull String toString() {
    return getClass().getSimpleName() + "[ " + namespace + " ]";
  }

}
