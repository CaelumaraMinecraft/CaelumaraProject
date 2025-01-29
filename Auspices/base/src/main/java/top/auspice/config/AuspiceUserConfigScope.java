package top.auspice.config;

import net.aurika.config.CompleteConfigPath;
import net.aurika.config.scope.ConfigScope;
import net.aurika.namespace.nested.NestedNamespace;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import top.auspice.api.user.AuspiceUser;

public class AuspiceUserConfigScope implements ConfigScope {
    private final @NotNull AuspiceUser user;

    public AuspiceUserConfigScope(@NotNull AuspiceUser user) {
        Checker.Arg.notNull(user, "user");
        this.user = user;
    }

    public @NotNull AuspiceUser getUser() {
        return user;
    }

    @Override
    public @NotNull NestedNamespace getNestedNamespace() {
        return AuspiceUser.getTopNestedNamespace(user);
    }

    @Override
    public boolean isAvailable(CompleteConfigPath completePath) {
        // TODO
    }
}
