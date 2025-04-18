package net.aurika.kingdoms.territories.commands.general.land.category

import org.kingdoms.commands.KingdomsParentCommand

class CommandLandCategory(parent: KingdomsParentCommand) : KingdomsParentCommand("category", parent) {
  init {
    CommandLandCategoryGet(this)
    CommandLandCategorySet(this)
  }
}