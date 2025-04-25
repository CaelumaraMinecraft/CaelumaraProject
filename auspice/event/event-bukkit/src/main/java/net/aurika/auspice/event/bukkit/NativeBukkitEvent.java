package net.aurika.auspice.event.bukkit;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NativeBukkitEvent {

  Class<? extends org.bukkit.event.Event> value();

}
