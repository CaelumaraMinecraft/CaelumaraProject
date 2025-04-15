package net.aurika.auspice.service.luckperms;

import net.aurika.auspice.service.api.BukkitService;
import net.aurika.configuration.sections.ConfigSection;
import net.aurika.configuration.sections.YamlNodeSection;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.data.NodeMap;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeBuilder;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.luckperms.api.node.metadata.NodeMetadataKey;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class ServiceLuckPerms implements ContextCalculator<Player>, BukkitService {

  private static final ServiceLuckPerms lpService;

  public ServiceLuckPerms() {
  }

  public static void init() {
  }

  public static CompletableFuture<Boolean> perform(Collection<UUID> players, Operation operation, Collection<Node> nodes) {
    LuckPerms luckPerms = getLPService();
    if (luckPerms == null) {
      return CompletableFuture.completedFuture(Boolean.FALSE);
    } else {
      UserManager var7 = luckPerms.getUserManager();
      ArrayList<CompletableFuture<Boolean>> booleanFutures = new ArrayList<>(players.size());

      for (UUID user : players) {
        CompletableFuture<User> var8 = var7.loadUser(user);
        booleanFutures.add(var8.thenApplyAsync((var2x) -> {
          NodeMap var6 = var2x.data();
          boolean b1 = true;

          for (Node node : nodes) {
            switch (operation) {
              case ADD:
                if (!var6.add(node).wasSuccessful()) {
                  b1 = false;
                }
                break;
              case REMOVE:
                if (!var6.remove(node).wasSuccessful()) {
                  b1 = false;
                }
                break;
              case CONTAINS:
                if (!var6.contains(node, NodeEqualityPredicate.EXACT).asBoolean()) {
                  b1 = false;
                }
                break;
              default:
                throw new AssertionError(operation);
            }
          }

          return b1;
        }));
      }

      return CompletableFuture.allOf(booleanFutures.toArray(new CompletableFuture[0])).thenApply(
          (var0x) -> Boolean.TRUE);
    }
  }

  public static List<Node> toNodes(Collection<String> var0) {
    return var0.stream().map((var0x) -> Node.builder(var0x).build()).collect(Collectors.toList());
  }

  public static CompletableFuture<Boolean> perform(Collection<UUID> players, Operation operation, org.snakeyaml.engine.v2.nodes.Node yamlNode) {
    if (yamlNode instanceof SequenceNode var5) {
      ArrayList<Node> var3 = new ArrayList<>();

      for (org.snakeyaml.engine.v2.nodes.Node var4 : var5.getValue()) {
        var3.add(buildNode(var4));
      }

      return perform(players, operation, var3);
    } else {
      return CompletableFuture.completedFuture(null);
    }
  }

  public static CompletableFuture<Boolean> perform(Collection<UUID> players, Operation operation, ConfigSection section) {
    List<?> list = section.getList();
    if (list != null) {
      ArrayList<Node> var3 = new ArrayList<>();

      for (org.snakeyaml.engine.v2.nodes.Node var4 : var5.getValue()) {
        var3.add(buildNode(var4));
      }

      return perform(players, operation, var3);
    }

    if (yamlNode instanceof SequenceNode var5) {
      ArrayList<Node> var3 = new ArrayList<>();

      for (org.snakeyaml.engine.v2.nodes.Node var4 : var5.getValue()) {
        var3.add(buildNode(var4));
      }

      return perform(players, operation, var3);
    } else {
      return CompletableFuture.completedFuture(null);
    }
  }

  @Deprecated
  public static Node buildNode(org.snakeyaml.engine.v2.nodes.Node yamlNode) {
    if (yamlNode == null) {
      throw new IllegalArgumentException("Unexpected permission type");
    }
    return buildNode(new YamlNodeSection(null, yamlNode));
  }

  public static Node buildNode(ConfigSection section) {
    String s = section.getConfigureString();
    if (!Strings.isNullOrEmpty(s)) {
      return Node.builder(s).build();
    }

    if (!section.hasSubSections()) {
      throw new IllegalArgumentException("Unexpected permission type: " + section);
    }

    {
      NodeBuilder<?, ?> nodeBuilder = Node.builder(
          Objects.requireNonNull(section.getString(new String[]{"permission"}), () -> "Permission is null " + section));

      Boolean valueBool = section.getBoolean(new String[]{"value"});
      if (valueBool != null) {
        nodeBuilder.value(valueBool);
      }

      Duration duration = section.getTime(new String[]{"expiry"}, new PlaceholderContextBuilder());
      if (duration != null) {
        nodeBuilder.expiry(duration.toMillis());
      }

      ConfigSection contextSection = section.getSection(new String[]{"context"});
      if (contextSection != null) {
        ImmutableContextSet.Builder contextBuilder = ImmutableContextSet.builder();
        for (String key : contextSection.getKeys()) {
          String contextStr = contextSection.getString(new String[]{key});
          if (contextStr != null) {
            contextBuilder.add(key, contextStr);
          }
        }

        nodeBuilder.context(contextBuilder.build());
      }

      ConfigSection metadataSection = section.getSection(new String[]{"metadata"});
      if (metadataSection != null) {
        try {
          Class<?> clazz = Class.forName(metadataSection.getString(new String[]{"class"}));
          NodeMetadataKey var11 = NodeMetadataKey.of(metadataSection.getString(new String[]{"name"}), clazz);
          Object var12 = clazz.cast(metadataSection.getParsed(new String[]{"value"}));
          nodeBuilder.withMetadata(var11, Fn.cast(var12));
        } catch (ClassNotFoundException var6) {
          throw new RuntimeException(var6);
        }
      }

      return nodeBuilder.build();
    }
  }

  private static LuckPerms getLPService() {
    RegisteredServiceProvider<LuckPerms> var0 = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    return var0 == null ? null : var0.getProvider();
  }

  public void disable() {
    LuckPerms luckPerms = getLPService();
    if (luckPerms != null) {
      luckPerms.getContextManager().unregisterCalculator(lpService);
    }
  }

  public void calculate(@NotNull Player var1, @NotNull ContextConsumer var2) {

  }

  public @NotNull ContextSet estimatePotentialContexts() {

  }

  static {
    LuckPerms luckPerms = getLPService();
    ServiceLuckPerms serviceLP = null;
    if (luckPerms != null) {
      serviceLP = new ServiceLuckPerms();
      luckPerms.getContextManager().registerCalculator(serviceLP);
      MessageHandler.sendConsolePluginMessage("&2Successfully registered LuckPerms context.");
    } else {
      MessageHandler.sendConsolePluginMessage("&cCould not register LuckPerms context.");
    }

    lpService = serviceLP;
  }

  public enum Operation {
    ADD,
    REMOVE,
    CONTAINS
  }

}
