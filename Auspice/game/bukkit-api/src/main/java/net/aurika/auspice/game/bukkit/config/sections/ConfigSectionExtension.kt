@file:JvmName("ConfigSectionExtension")

package net.aurika.auspice.game.bukkit.config.sections

import org.bukkit.configuration.ConfigurationSection
import net.aurika.config.sections.ConfigSection

fun ConfigSection.toBukkitConfigurationSection(): ConfigurationSection {
    return BukkitConfigurationSection(this)
}