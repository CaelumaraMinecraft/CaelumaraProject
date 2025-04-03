package net.aurika.auspice.service.luckperms;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.config.accessor.YamlClearlyConfigAccessor;
import net.aurika.config.sections.ConfigSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.util.*;

public class LuckPermsConfigUtils {

  public void addAllPermissions(@NotNull Collection<UUID> var1, @NotNull ConfigSection var2, int var3) {
    Objects.requireNonNull(var1);
    Objects.requireNonNull(var2);
    Map var9 = new HashMap();
    Iterator var4 = var2.getKeys().iterator();

    while (true) {
      do {
        if (!var4.hasNext()) {
          var4 = var1.iterator();

          while (var4.hasNext()) {
            UUID var11 = (UUID) var4.next();
            LuckPermsConfigUtils.perform(var1, ServiceLuckPerms.Operation.REMOVE, var9.values());
          }

          return;
        }
      } while (!Intrinsics.areEqual(var4.next(), "permission"));

      Node var10000 = var2.getNode("permission");
      Intrinsics.checkNotNull(var10000);
      Iterator var5 = ((SequenceNode) var10000).getValue().iterator();

      while (var5.hasNext()) {
        net.luckperms.api.node.Node var6 = LuckPermsConfigUtils.buildNode((Node) var5.next());
        String var10 = var6.getKey();
        Intrinsics.checkNotNullExpressionValue(var10, "");
        String var8 = var10;
        Intrinsics.checkNotNull(var6);
        var9.put(var8, var6);
      }
    }
  }

  public void removeAllPermissions(@NotNull Collection<UUID> var1, @NotNull ConfigSection var2) {
    Objects.requireNonNull(var1, "");
    Objects.requireNonNull(var2, "");
    Map var3 = new HashMap();
    Iterator var4 = var2.getKeys().iterator();

    while (true) {
      while (var4.hasNext()) {
        String var5 = (String) var4.next();
        Node var6 = var2.getNode(var5);
        if (Intrinsics.areEqual(var5, "permission")) {
          Intrinsics.checkNotNull(var6);
          SequenceNode var9;
          Iterator var10 = (var9 = (SequenceNode) var6).getValue().iterator();

          while (var10.hasNext()) {
            net.luckperms.api.node.Node var12 = LuckPermsConfigUtils.buildNode((Node) var10.next());
            String var10000 = var12.getKey();
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            String var8 = var10000;
            Intrinsics.checkNotNull(var12);
            var3.put(var8, var12);
          }
        } else if (var6 instanceof MappingNode) {
          this.removeAllPermissions(var1, new ConfigSection((MappingNode) var6));
        }
      }

      var4 = var1.iterator();

      while (var4.hasNext()) {
        UUID var11 = (UUID) var4.next();
        LuckPermsConfigUtils.perform(var1, Operation.REMOVE, var3.values());
      }

      return;
    }
  }

  public void performPermissionOperations(@NotNull Collection<UUID> var1, @NotNull ConfigSection var2, @Nullable ConfigSection var3, int var4, @NotNull LuckPermsConfigUtils.Operation var5, @NotNull MessagePlaceholderProvider var6) {
    Objects.requireNonNull(var1, "");
    Objects.requireNonNull(var2, "");
    Objects.requireNonNull(var5, "");
    Objects.requireNonNull(var6, "");
    KingdomsConfig.getClosestLevelSection(
        (ConfigAccessor) (new YamlClearlyConfigAccessor(var2, var2)).noDefault(), var4);
    String[] var7;
    (var7 = new String[1])[0] = String.valueOf(var4);
    if ((var3 = var2.getSection(var7)).isSet("permission")) {
      String var10000 = var3.getString("previous");
      if (var10000 != null) {
        String var9 = var10000;
        switch (var10000.hashCode()) {
          case -934610812:
            if (var9.equals("remove")) {
              if (var4 <= 1) {
                throw new IllegalArgumentException("Cannot remove previous permission from lowest level");
              }

              ConditionChain.Companion.performPermissionOperations(
                  var1, var2, (ConfigSection) null, var4 - 1, Operation.REMOVE, var6);
            }
        }
      }

      LuckPermsConfigUtils.perform(var1, var5, var3.getNode("permission"));
    }

    Iterator var10 = var3.getKeys().iterator();

    while (var10.hasNext()) {
      String var8;
      if (ConditionProcessor.process(
          ConditionCompiler.compile(var8 = (String) var10.next()).evaluate(), (PlaceholderProvider) var6)) {
        Node var11;
        if (!((var11 = var3.getNode(var8)) instanceof MappingNode)) {
          throw new IllegalStateException(("Unexpected permission " + var11.getWholeMark()).toString());
        }

        this.performPermissionOperations(var1, var2, new ConfigSection((MappingNode) var11), var4, var5, var6);
      }
    }
  }

  @NotNull
  public ConditionChain<Object> parse(@NotNull ConfigSection var1, @NotNull Function<String, Object> var2) {
    Objects.requireNonNull(var1, "");
    Objects.requireNonNull(var2, "");
    List var3 = new ArrayList();
    Iterator<String> var4 = var1.getKeys().iterator();

    while (true) {
      while (var4.hasNext()) {
        String var5;
        ConditionCompiler.LogicalOperand var6 = ConditionCompiler.compile(var5 = (String) var4.next()).evaluate();
        Node var7;
        Node var8;
        if ((var8 = var7 = var1.getNode(var5)) instanceof ScalarNode) {
          a(var3, var6, CollectionsKt.listOf(var2.apply(((ScalarNode) var7).getValue())));
        } else if (!(var8 instanceof SequenceNode)) {
          if (var8 instanceof MappingNode) {
            ConditionalCompiler.LogicalOperand var10001 = var6;
            String[] var15;
            (var15 = new String[1])[0] = var5;
            ConfigSection var10003 = var1.getSection(var15);
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            a(var3, var10001, this.parse(var10003, var2));
          }
        } else {
          List var10002 = ((SequenceNode) var7).getValue();
          Intrinsics.checkNotNullExpressionValue(var10002, "");
          Iterable var11 = var10002;
          ConditionCompiler.LogicalOperand var9 = var6;
          Iterable var13 = var11;
          Collection var12 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));
          Iterator var14 = var13.iterator();

          while (var14.hasNext()) {
            Object var16 = var14.next();
            var7 = (Node) var16;
            Intrinsics.checkNotNull(var7);
            var12.add(var2.apply(((ScalarNode) var7).getValue()));
          }

          List var10 = (List) var12;
          a(var3, var9, var10);
        }
      }

      return new ConditionChain(var3);
    }
  }

  private static void a(List<ConditionBranch<Object>> var0, ConditionCompiler.LogicalOperand var1, Object var2) {
    Intrinsics.checkNotNull(var1);
    var0.add(new ConditionBranch<>(var1, var2));
  }

}