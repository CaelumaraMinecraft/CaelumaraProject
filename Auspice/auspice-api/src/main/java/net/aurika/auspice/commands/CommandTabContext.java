package net.aurika.auspice.commands;

import net.aurika.auspice.configs.messages.AuspiceLang;
import net.aurika.auspice.loader.AuspiceLoader;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.core.Server;
import net.aurika.auspice.server.player.OfflinePlayer;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.translation.messenger.DefaultedMessenger;
import net.aurika.auspice.translation.messenger.Messenger;
import net.aurika.auspice.translation.messenger.StaticMessenger;
import net.aurika.auspice.utils.Pair;
import net.aurika.auspice.utils.string.Strings;
import net.aurika.auspice.utils.unsafe.functional.SecondarySupplier;
import net.aurika.util.number.AnyNumber;
import net.aurika.util.number.NumberConstraint;
import net.aurika.util.string.QuantumString;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandTabContext extends CommandContext {

  public CommandTabContext(AuspiceLoader var1, KingdomsCommand var2, CommandSender var3, String[] var4) {
    super(var1, var2, var3, var4);
  }

  private static @NonNull List<String> a(@NonNull Map<QuantumString, UUID> var0, @Nullable QuantumString var1, @Nullable Predicate<String> var2) {
    ArrayList<String> var3 = new ArrayList<>(Math.max(var0.size() / Math.max(1, var1.length()), 30));
    String var4 = var1.getQuantum();
    Iterator<QuantumString> var7 = var0.keySet().iterator();

    while (true) {
      QuantumString var5;
      String var6;
      do {
        if (!var7.hasNext()) {
          return var3;
        }

        var6 = (var5 = (QuantumString) var7.next()).getQuantum();
      } while (var2 != null && !var2.test(var6));

      if (var1.isEmpty()) {
        var3.add(var6);
      } else if (var5.getQuantum().startsWith(var4)) {
        var3.add(var6);
      }
    }
  }

  public String tab(String var1) {
    return (var1 = this.lang("tab-completion", var1).parse(super.messageSettings)) == null ? "" : var1;
  }

  public Stream<String> filter(Stream<String> var1, int var2) {
    return !this.assertArgs(var2 - 1) ? var1 : var1.filter((var2x) -> var2x.startsWith(this.arg(var2)));
  }

  public List<String> suggest(int var1, Collection<String> var2) {
    return this.suggest(var1, var2.toArray(new String[0]));
  }

  public List<String> suggest(int var1, String... var2) {
    String var8;
    if ((var8 = this.arg(var1)).isEmpty()) {
      return Arrays.asList(var2);
    } else {
      ArrayList<String> var3 = new ArrayList<>(var2.length);
      var8 = Strings.toLatinLowerCase(var8);
      String[] var4 = var2;
      int var5 = var2.length;

      int var6;
      String var7;
      for (var6 = 0; var6 < var5; ++var6) {
        if (Strings.toLatinLowerCase(var7 = var4[var6]).startsWith(var8)) {
          var3.add(var7);
        }
      }

      if (var3.size() <= 5) {
        var4 = var2;
        var5 = var2.length;

        for (var6 = 0; var6 < var5; ++var6) {
          if (Strings.toLatinLowerCase(var7 = var4[var6]).contains(var8)) {
            var3.add(var7);
          }
        }
      }

      return var3;
    }
  }

  public @NonNull List<String> getPlayers(int var1) {
    return this.getPlayers(var1, null);
  }

  public List<String> add(List<String> var1, String... var2) {
    var1.addAll(Arrays.asList(var2));
    return var1;
  }

  public @NonNull List<String> getPlayers(int var1, Predicate<OfflinePlayer> var2) {
    return this.getPlayers(Server.get().playerManager().getOnlinePlayers(), this.arg(var1), false, var2);
  }

  @NonNull
  public List<String> getPlayers(Collection<? extends OfflinePlayer> var1, @NonNull String var2, boolean var3, Predicate<OfflinePlayer> var4) {
    return TabCompleteManager.getPlayers(var1, var2, var3, var4);
  }

  public String currentArg() {
    return super.args[super.args.length - 1];
  }

  private String a(Messenger var1) {
    TextObject var4 = var1.getMessageObject(super.messageSettings.diversity());
    if (!KingdomsCommand.COLORIZE_TAB_COMPLETES) {
      super.messageSettings.ignoreColors();
    }

    String var5;
    try {
      var5 = var4.buildPlain(super.messageSettings);
    } finally {
      super.messageSettings.ignoreColors(false);
    }

    return var5;
  }

  public List<String> tabComplete(Messenger var1) {
    return Collections.singletonList(this.a(var1));
  }

  public List<String> tabComplete(Messenger... var1) {
    return Arrays.stream(var1).map(this::a).collect(Collectors.toList());
  }

  public List<String> tabComplete(String... var1) {
    return this.tabComplete(Arrays.asList(var1));
  }

  public List<String> tabComplete(Collection<String> var1) {
    ArrayList<String> var2 = new ArrayList<>(var1.size());

    for (String var3 : var1) {
      var2.add(KingdomsCommand.processTabMessage(var3));
    }

    return var2;
  }

  protected static OfflinePlayer getPlayer(CommandSender var0, String var1) {
    OfflinePlayer var2;
    if ((var2 = PlayerUtils.getOfflinePlayer(var1)) == null) {
      AuspiceLang.NOT_FOUND_PLAYER.sendError(var0, new Object[0]);
    }

    return var2;
  }

  public List<String> emptyTab() {
    return Collections.emptyList();
  }

  public Messenger tabParameter(String var1, boolean var2) {
    int var3 = var2 ? 60 : 91;
    int var4 = var2 ? 62 : 93;
    String var5 = var2 ? "{$e}" : "{$p}";
    return DefaultedMessenger.oneOf(
        this.getCommand().lang("tab-completion-" + (var2 ? "required" : "optional"), var1),
        () -> this.getCommand().lang("tab-completion", var1),
        () -> new StaticMessenger("{$sep}" + var3 + var5 + var1 + "{$sep}" + var4)
    );
  }

  public Completer tabCompleteNumberModifier(String var1, NumberConstraint... var2) {
    int var3 = this.nextArg();
    if (this.isAtCurrentParameter()) {
      Messenger var4 = this.tabParameter(var1, true);
      Pair<AnyNumber, Messenger> var5 = this.getNumber(var3, var4, var2);
      if (var5.getKey() != null) {
        return this.complete(var4);
      }

      Messenger var6;
      if (var5.getValue() != null && (var6 = (Messenger) var5.getValue()) != AuspiceLang.INVALID_NUMBER) {
        return this.complete(var6);
      }

      String var9 = super.args[var3];
      if (this.getNumberModifierType(var9) == null) {
        this.var("setter_type", var9);
        List var8;
        if ((var8 = this.suggest(
            var3, Stream.of(
                AuspiceLang.SETTERS_TYPE_ADD, AuspiceLang.SETTERS_TYPE_SUBTRACT,
                AuspiceLang.SETTERS_TYPE_SET
            ).flatMap((var1x) -> Arrays.stream(Strings.splitArray(var1x.parse(super.messageSettings), ' '))).collect(
                Collectors.toList())
        )).isEmpty() && !var9.isEmpty()) {
          var8.add(AuspiceLang.SETTERS_TYPE_UNKNOWN.parse(super.messageSettings));
        }

        return this.complete(var8);
      }

      this.a();
    }

    if (this.hasMoreParameters()) {
      int var10 = this.nextArg();
      if (this.isAtCurrentParameter()) {
        String var7 = super.args[var10 - 1];
        if (this.getNumberModifierType(var7) != null) {
          return this.complete(this.tabParameter(var1, true));
        }

        return this.a();
      }
    }

    return this.a();
  }

  private Completer a() {
    return new Completer(null);
  }

  public Completer complete(Messenger... var1) {
    return this.complete(this.tabComplete(var1));
  }

  public Completer complete(List<String> var1) {
    return new Completer(var1);
  }

  public final class Completer {

    private List<String> a;

    public Completer(List<String> var2) {
      this.a = var2;
    }

    public Completer then(SecondarySupplier<List<String>> var1) {
      if (this.a == null) {
        CommandTabContext.this.nextArg();
        if (CommandTabContext.this.isAtCurrentParameter()) {
          this.a = var1.get();
        }
      }

      return this;
    }

    public Completer then(Supplier<Completer> var1) {
      if (this.a == null) {
        this.a = var1.get().a;
      }

      return this;
    }

    public List<String> build() {
      return this.a == null ? new ArrayList<>() : this.a;
    }

  }

}
