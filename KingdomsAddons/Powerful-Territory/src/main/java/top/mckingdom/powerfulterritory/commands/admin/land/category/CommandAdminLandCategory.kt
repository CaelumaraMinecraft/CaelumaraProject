package top.mckingdom.powerfulterritory.commands.admin.land.category

import org.kingdoms.commands.KingdomsParentCommand

class CommandAdminLandCategory(parent: KingdomsParentCommand) : KingdomsParentCommand("category", parent) {
    init {
        CommandAdminLandCategoryGet(this)
        CommandAdminLandCategorySet(this)
    }

}
