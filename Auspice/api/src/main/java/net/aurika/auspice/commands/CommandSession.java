package net.aurika.auspice.commands;

import com.github.benmanes.caffeine.cache.Cache;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import net.aurika.common.key.namespace.NSedKey;
import net.aurika.common.key.namespace.NSKeyMap;
import net.aurika.auspice.configs.messages.AuspiceLang;
import net.aurika.auspice.configs.messages.SimpleMessenger;
import net.aurika.auspice.translation.messenger.Messenger;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.server.entity.Player;
import top.auspice.utils.PlayerUtils;
import net.aurika.util.number.AnyNumber;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;


public final class CommandSession extends KingdomsCommand {
    @NotNull
    public static final Companion Companion = new Companion();
    @NotNull
    private static final AtomicInteger a = new AtomicInteger(0);
    @NotNull
    public static final NSKeyMap<Cache<Integer, Session>> SESSIONS = new NSKeyMap<>();

    public CommandSession() {
        super("session");
    }

    @NotNull
    public CommandResult execute(@NotNull CommandContext var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        if (var1.requireArgs(3)) {
            return CommandResult.FAILED;
        } else {
            NSedKey var10000 = NSedKey.fromString(var1.arg(0));
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            NSedKey var2 = var10000;
            AnyNumber var6 = var1.getNumber(1, true, false, null);
            if (var6 != null) {
                Number var7 = var6.getValue();
                if (var7 != null) {
                    int var3 = var7.intValue();
                    Cache var5;
                    Cache var8 = var5 = (Cache) SESSIONS.get(var2);
                    CommandResult var10;
                    if (var8 != null) {
                        Session var9 = (Session) var8.getIfPresent(var3);
                        if (var9 != null) {
                            Session var4 = var9;
                            if (!var9.getAuthorizedBy().test(PlayerUtils.getUniqueId(((SimpleMessenger) var1).messageReceiver()))) {
                                var10 = var1.fail((Messenger) AuspiceLang.COMMAND_SESSION_EXPIRED);
                                Intrinsics.checkNotNullExpressionValue(var10, "");
                                return var10;
                            }

                            SessionCallback var11 = var4.getSessionCallback();
                            String var10001 = var1.joinArgs("", 2);
                            Intrinsics.checkNotNullExpressionValue(var10001, "");
                            if (var11.callback(var10001)) {
                                var5.put(var3, var4);
                            } else {
                                var5.invalidate(var3);
                            }

                            return CommandResult.SUCCESS;
                        }
                    }

                    var10 = var1.fail((Messenger) AuspiceLang.COMMAND_SESSION_EXPIRED);
                    Intrinsics.checkNotNullExpressionValue(var10, "");
                    return var10;
                }
            }

            return CommandResult.FAILED;
        }
    }

    @Internal
    @NotNull
    public static Session addSession(@NotNull Player var0, @NotNull NSedKey var1, @NotNull Duration var2, @NotNull MessageContextImpl var3, @NotNull SessionCallback var4) {
        return Companion.addSession(var0, var1, var2, var3, var4);
    }

    @Internal
    @NotNull
    public static Session addSession(@NotNull CommandContext var0, @NotNull Duration var1, @NotNull SessionCallback var2) {
        return Companion.addSession(var0, var1, var2);
    }

    public static final class Companion {
        private Companion() {
        }

        private static Cache<Integer, Session> a() {
            Cache var10000 = CacheHandler.newBuilder().expireAfter((Expiry) (new Expiry<Integer, Session>() {
                public long expireAfterCreate(int var1, Session var2, long var3) {
                    Intrinsics.checkNotNullParameter(var2, "");
                    return var2.getExpiresAfter().toNanos();
                }

                public long expireAfterUpdate(int var1, Session var2, long var3, long var5) {
                    Intrinsics.checkNotNullParameter(var2, "");
                    return var5;
                }

                public long expireAfterRead(int var1, Session var2, long var3, long var5) {
                    Intrinsics.checkNotNullParameter(var2, "");
                    return var5;
                }
            })).build();
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            return var10000;
        }

        private static int b() {
            if (CommandSession.a.get() >= Integer.MAX_VALUE) {
                CommandSession.a.set(0);
                return 0;
            } else {
                return CommandSession.a.getAndIncrement();
            }
        }

        @Internal
        @NotNull
        public Session addSession(@NotNull Player var1, @NotNull NSedKey var2, @NotNull Duration var3, @NotNull MessageContextImpl var4, @NotNull SessionCallback var5) {
            Intrinsics.checkNotNullParameter(var1, "");
            Intrinsics.checkNotNullParameter(var2, "");
            Intrinsics.checkNotNullParameter(var3, "");
            Intrinsics.checkNotNullParameter(var4, "");
            Intrinsics.checkNotNullParameter(var5, "");
            Session var6 = new Session(var2, b(), Companion::a, var3, var5);
            var4.raw("command_session_id", var6.getId());
            var4.raw("command_namespace", var6.getNamespace().asString());
            Map var7 = (Map) CommandSession.SESSIONS;
            NSedKey var8 = var6.getNamespace();
            Object var9;
            Object var11;
            if ((var9 = var7.get(var8)) == null) {
                Cache var10 = a();
                var7.put(var8, var10);
                var11 = var10;
            } else {
                var11 = var9;
            }

            ((Cache) var11).put(var6.getId(), var6);
            return var6;
        }

        @Internal
        @NotNull
        public Session addSession(@NotNull CommandContext var1, @NotNull Duration var2, @NotNull SessionCallback var3) {
            Intrinsics.checkNotNullParameter(var1, "");
            Intrinsics.checkNotNullParameter(var2, "");
            Intrinsics.checkNotNullParameter(var3, "");
            StringBuilder var10002 = new StringBuilder("COMMAND_");
            String var10003 = var1.getCommand().getName();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            String var4 = var10003;
            Locale var12 = Locale.ENGLISH;
            Intrinsics.checkNotNullExpressionValue(var12, "");
            var10003 = var4.toUpperCase(var12);
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            NSedKey var11 = NSedKey.auspice(var10002.append(var10003).toString());
            Intrinsics.checkNotNullExpressionValue(var11, "");
            Session var6 = new Session(var11, b(), Companion::a, var2, var3);
            var1.messageContext().raw("command_session_id", var6.getId());
            var1.messageContext().raw("command_namespace", var6.getNamespace().asString());
            Map var9 = (Map) CommandSession.SESSIONS;
            NSedKey var5 = var6.getNamespace();
            Object var7;
            Object var10;
            if ((var7 = var9.get(var5)) == null) {
                Companion var10000 = CommandSession.Companion;
                Cache var8 = a();
                var9.put(var5, var8);
                var10 = var8;
            } else {
                var10 = var7;
            }

            ((Cache) var10).put(var6.getId(), var6);
            return var6;
        }

        private static boolean a(Player var0, UUID var1) {
            Intrinsics.checkNotNullParameter(var0, "");
            Intrinsics.checkNotNullParameter(var1, "");
            return Intrinsics.areEqual(var1, PlayerUtils.getUniqueId(var0));
        }

        private static boolean a(CommandContext var0, UUID var1) {
            Intrinsics.checkNotNullParameter(var0, "");
            Intrinsics.checkNotNullParameter(var1, "");
            return Intrinsics.areEqual(var1, PlayerUtils.getUniqueId(((SimpleMessenger) var0).messageReceiver()));
        }
    }

    public static final class Session {
        @NotNull
        private final NSedKey NSedKey;
        private final int id;
        @NotNull
        private final Predicate<UUID> authorizedBy;
        @NotNull
        private final Duration expiresAfter;
        @NotNull
        private final SessionCallback sessionCallback;

        public Session(@NotNull NSedKey var1, int var2, @NotNull Predicate<UUID> var3, @NotNull Duration var4, @NotNull SessionCallback var5) {
            Intrinsics.checkNotNullParameter(var1, "");
            Intrinsics.checkNotNullParameter(var3, "");
            Intrinsics.checkNotNullParameter(var4, "");
            Intrinsics.checkNotNullParameter(var5, "");
            this.NSedKey = var1;
            this.id = var2;
            this.authorizedBy = var3;
            this.expiresAfter = var4;
            this.sessionCallback = var5;
        }

        @NotNull
        public NSedKey getNamespace() {
            return this.NSedKey;
        }

        public int getId() {
            return this.id;
        }

        @NotNull
        public Predicate<UUID> getAuthorizedBy() {
            return this.authorizedBy;
        }

        @NotNull
        public Duration getExpiresAfter() {
            return this.expiresAfter;
        }

        @NotNull
        public SessionCallback getSessionCallback() {
            return this.sessionCallback;
        }
    }

    @FunctionalInterface
    public interface SessionCallback {
        boolean callback(@NotNull String var1);
    }
}

