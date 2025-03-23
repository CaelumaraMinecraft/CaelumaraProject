package net.aurika.auspice.config;

import net.aurika.auspice.user.AuspiceUser;
import net.aurika.common.key.namespace.nested.NestedNamespace;
import net.aurika.config.CompleteConfigPath;
import net.aurika.config.scope.ConfigScope;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class AuspiceUserConfigScope implements ConfigScope {
    private final @NotNull AuspiceUser user;

    public AuspiceUserConfigScope(@NotNull AuspiceUser user) {
        Validate.Arg.notNull(user, "user");
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
