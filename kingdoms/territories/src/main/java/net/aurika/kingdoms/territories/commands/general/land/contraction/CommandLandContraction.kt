package net.aurika.kingdoms.territories.commands.general.land.contraction

import org.kingdoms.commands.KingdomsParentCommand

class CommandLandContraction(parent: KingdomsParentCommand) : KingdomsParentCommand("contraction", parent) {
  init {
    CommandLandContractionGet(this)
    CommandLandContractionAllocate(this)
    CommandLandContractionDeallocate(this)
  }
}