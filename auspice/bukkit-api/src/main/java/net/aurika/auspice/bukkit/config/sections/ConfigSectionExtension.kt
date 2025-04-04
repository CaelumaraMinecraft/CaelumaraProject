@file:JvmName("ConfigSectionExtension")

package net.aurika.auspice.bukkit.config.sections

import net.aurika.configuration.sections.ConfigSection
import org.bukkit.configuration.ConfigurationSection

fun ConfigSection.toBukkitConfigurationSection(): ConfigurationSection {
  return BukkitConfigurationSection(this)
}