package top.mckingdom.powerfulterritory.configs;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.config.annotations.Comment;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum PowerfulTerritoryLang implements DefinedMessenger {
  COMMAND_ADMIN_DOMAIN_NAME("land", 1, 2, 3),
  COMMAND_ADMIN_DOMAIN_ALIASES("terra territory", 1, 2, 3),
  COMMAND_ADMIN_DOMAIN_DESCRIPTION("{$s}Manage anything related to land from auspice addon.", 1, 2, 3),

  COMMAND_ADMIN_DOMAIN_CATEGORY_NAME("category", 1, 2, 3, 4),
  COMMAND_ADMIN_DOMAIN_CATEGORY_DESCRIPTION("{$s}Manage land categories.", 1, 2, 3, 4),

  COMMAND_ADMIN_DOMAIN_CATEGORY_GET_SUCCESS("{$p}The land category of land %location% is %category%.", 1, 2, 3, 4, 5),
  COMMAND_ADMIN_DOMAIN_CATEGORY_GET_FAILED_NOT_CLAIMED(
      "{$e}The land is not claimed, it don't have category.", 1, 2, 3, 4, 5, 6),
  COMMAND_ADMIN_DOMAIN_CATEGORY_SET_SUCCESS(
      "{$p}The land category of land %location% has been changed from %old-category% to %new-category%.", 1, 2, 3, 4,
      5
  ),
  COMMAND_ADMIN_DOMAIN_CATEGORY_SET_FAILED_NOT_CLAIMED(
      "{$e}The land is not claimed, you can't set category of this land.", 1, 2, 3, 4, 5, 6),

  COMMAND_ADMIN_DOMAIN_CONTRACTION_NAME("contraction", 1, 2, 3, 4),
  COMMAND_ADMIN_DOMAIN_CONTRACTION_DESCRIPTION("{$s}Manage land categories.", 1, 2, 3, 4),

  COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_SUCCESS_HEAD(
      "{$sep}&l-------=( {$sep}%kingdom% {$p}Land contractions {$sep})=-------", 1, 2, 3, 4, 5, 6),
  COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_SUCCESS_BODY(
      "  {$s}%contraction%:" +
          "\n    &7%players%",
      1, 2, 3, 4, 5, 6
  ),
  COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_SUCCESS_END(
      "{$p}&l---------------------------------------------------", 1, 2, 3, 4, 5, 6),
  COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_FAILED_NOT_CLAIMED(
      "{$e}The land is not claimed, it don't have contractions.", 1, 2, 3, 4, 5, 6),
  COMMAND_ADMIN_DOMAIN_CONTRACTION_SET_SUCCESS(
      "{$p}The land contractions of land %location% has been changed from %old-contraction% to %new-contraction%.", 1,
      2, 3, 4, 5
  ),
  COMMAND_ADMIN_DOMAIN_CONTRACTION_SET_FAILED_NOT_CLAIMED(
      "{$e}The land is not claimed, you can't allocate contractions of this land.", 1, 2, 3, 4, 5, 6),

  COMMAND_DOMAIN_NAME("land", 1, 2),
  COMMAND_DOMAIN_ALIASES("terra landControl", 1, 2),
  COMMAND_DOMAIN_DESCRIPTION("{$s}Control the anything of a land.", 1, 2),

  COMMAND_DOMAIN_CATEGORY_NAME("category", 1, 2, 3),
  COMMAND_DOMAIN_CATEGORY_ALIASES("category", 1, 2, 3),
  COMMAND_DOMAIN_CATEGORY_DESCRIPTION("{$s}Manage land categories.", 1, 2, 3),

  COMMAND_DOMAIN_CATEGORY_GET_ALIASES("get see", 1, 2, 3, 4),
  COMMAND_DOMAIN_CATEGORY_GET_DESCRIPTION("{$s}Get the land category of the land you stand on.", 1, 2, 3, 4),
  COMMAND_DOMAIN_CATEGORY_GET_SUCCESS(
      "{$p}The land category of land {$s}%location% {$p}is {$s}%category%.", 1, 2, 3, 4),
  COMMAND_DOMAIN_CATEGORY_GET_FAILED_OTHER_KINGDOM(
      "{$e}You can't see the category of other kingdom's land.", 1, 2, 3, 4, 5),
  COMMAND_DOMAIN_CATEGORY_GET_FAILED_NOT_CLAIMED("{$e}You can't see the category in not claimed land.", 1, 2, 3, 4, 5),

  COMMAND_DOMAIN_CATEGORY_SET_USAGE("land category change <newCategory>", 1, 2, 3, 4),
  COMMAND_DOMAIN_CATEGORY_SET_ALIASES("change", 1, 2, 3, 4),
  COMMAND_DOMAIN_CATEGORY_SET_DESCRIPTION("{$s}Change the land category of the land you stand on.", 1, 2, 3, 4),
  COMMAND_DOMAIN_CATEGORY_SET_SUCCESS("{$p}Successfully set the land category to {$s}%new-category%.", 1, 2, 3, 4),
  COMMAND_DOMAIN_CATEGORY_SET_FAILED_OTHER_KINGDOM("{$e}Cat not change other kingdom's land category.", 1, 2, 3, 4, 5),
  COMMAND_DOMAIN_CATEGORY_SET_FAILED_NOT_CLAIMED(
      "{$e}Cat not change land category in not claimed land.", 1, 2, 3, 4, 5),

  COMMAND_DOMAIN_CONTRACTION_NAME("contraction", 1, 2, 3),
  COMMAND_DOMAIN_CONTRACTION_DESCRIPTION("{$s}Manage land contractions.", 1, 2, 3),

  COMMAND_DOMAIN_CONTRACTION_GET_ALIASES("see", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_GET_DESCRIPTION("{$s}Get all the contractions of the land you stand on.", 1, 2, 3, 4),
  @Comment("Available placeholders: %kingdom%")
  COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_HEAD("{$sep}-------=( {$p}Land contractions {$sep})=-------", 1, 2, 3, 4, 5),
  COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_BODY(
      "  {$s}%contraction%: " +
          "\n    &7%players%",
      1, 2, 3, 4, 5
  ),
  COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_END("{$p}+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+", 1, 2, 3, 4, 5),
  COMMAND_DOMAIN_CONTRACTION_GET_FAILED_NOT_CLAIMED("{$e}This land is not claimed!", 1, 2, 3, 4, 5),
  COMMAND_DOMAIN_CONTRACTION_GET_FAILED_OTHER_KINGDOM(
      "{$e}You can not see the contractions in other kingdom's land!", 1, 2, 3, 4, 5),

  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_USAGE("{$usage}landContraction allocation <contraction> <player>", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_ALIASES("allocate", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_DESCRIPTION("{$s}Allocation the contraction to a player.", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_SUCCESS(
      "{$p}Successfully allocated the player {$s}%player% {$p}a {$s}%duration% {$s}%contraction% {$p}contracting project",
      1, 2, 3, 4
  ),
  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_ALREADY_SET(
      "{$e}Already allocation contraction {$p}%contraction% {$e}for player {$p}%player%{$e}!", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_OTHER_KINGDOM(
      "{$e}You can not allocation contractions for other kingdom's land!", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_NOT_CLAIMED("{$e}This land isn't claimed!", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_ALLOCATE_FAILED_NO_PERMISSION(
      "{$e}You don't have permission or contraction to allocate.", 1, 2, 3, 4),

  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_NAME("deallocate", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_USAGE(
      "{$usage}land contraction deallocate <contraction> <receiver> [allocator]", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_ALIASES("deallocate", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_DESCRIPTION("{$s}retract the allocated contraction", 1, 2, 3, 4),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_SUCCESS_SINGLE_ALLOCATOR(
      "{$p}Successful deallocate land contraction %contraction% for player %receiver% for allocator %allocator% in land %location%",
      1, 2, 3, 4, 5
  ),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_SUCCESS_ALL_ALLOCATOR(
      "{$p}Successful deallocate land contraction %contraction% for player %receiver% in land %location%", 1, 2, 3, 4,
      5
  ),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_NOT_CLAIMED(
      "{$e}The land is not claimed, you can't deallocate contractions of this land.", 1, 2, 3, 4, 5),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_OTHER_KINGDOM(
      "{$e}You can't manage other kingdom's land contraction.", 1, 2, 3, 4, 5),
  COMMAND_DOMAIN_CONTRACTION_DEALLOCATE_FAILED_OTHER_ALLOCATOR(
      "{$e}You can't deallocate other allocators' allocation.", 1, 2, 3, 4, 5),

  COMMAND_ADMIN_DOMAIN_CLEARDATA_NAME("clearData", 1, 2, 3, 4),
  COMMAND_ADMIN_DOMAIN_CLEARDATA_SUCCESS("{$p}Successful clear land data %type%", 1, 2, 3, 4),
  COMMAND_ADMIN_DOMAIN_CLEARDATA_FAILED_NULL_LAND("{$e}The land is null!", 1, 2, 3, 4),

  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_NAME("invade-protection", 1, 2, 3, 4),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_ALIASES("uninvade", 1, 2, 3, 4),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_DESCRIPTION(
      "{$s}Change the state so that the land is no longer invaded", 1, 2, 3, 4),

  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_USAGES("{$usage} admin uninvade set <type> [x] [z]", 1, 2, 3, 4, 5),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_DESCRIPTION("{$s}Set the stats of invade protection", 1, 2, 3, 4, 5),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_SUCCESS(
      "{$p}Success to set the invade protection stat of the land: {$s}%new-status%", 1, 2, 3, 4, 5),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_FAILED_NOT_CLAIMED("{$e}This land isn't claimed!", 1, 2, 3, 4, 5, 6),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_FAILED_UNKNOWN_STATUS(
      "{$e}Unknown invade protection status.", 1, 2, 3, 4, 5, 6),

  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_GET_USAGES("{$usage} admin land invade-protection get [x] [z]", 1, 2, 3, 4, 5),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_GET_DESCRIPTION(
      "{$s}See the invade protection stat of the land", 1, 2, 3, 4, 5),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_GET_SUCCESS(
      "{$p}Invade protection status of this land is: {$sp}%status%", 1, 2, 3, 4, 5),
  COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_GET_FAILED_NOT_CLAIMED("{$s}This land isn't claimed", 1, 2, 3, 4, 5),

  POWERFUL_TERRITORY_INVADE_PROTECTION("{$e}This land has a invade protection, you can't invade this land", 2),

  POWERFUL_TERRITORY_ELYTRA_PROTECTION("{$e}You can't fly with elytra in this kingdom's land", 2),

  SENDER_ONLY_CONSOLE("{$e}This command can only be executed from the console."),
  SENDER_ONLY_PLAYER("{$e}This command can only be executed from a player."),


  ;

  private final @NotNull LanguageEntry entry;
  private final @NotNull String defaultValue;

  PowerfulTerritoryLang(@NotNull String defaultValue, int @NotNull ... grouped) {
    this.defaultValue = defaultValue;
    this.entry = DefinedMessenger.getEntry(null, this, grouped);
  }

  @Override
  public LanguageEntry getLanguageEntry() {
    return entry;
  }

  @Override
  public String getDefaultValue() {
    return this.defaultValue;
  }
}
