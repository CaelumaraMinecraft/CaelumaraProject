@file:JvmName("ConfigSectionExtension")

package net.aurika.auspice.game.bukkit.config.sections

import net.aurika.config.sections.ConfigSection
import org.bukkit.configuration.ConfigurationSection

fun ConfigSection.toBukkitConfigurationSection(): ConfigurationSection {
  return BukkitConfigurationSection(this)
}