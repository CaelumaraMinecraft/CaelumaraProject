@file:JvmName("LandCategories")
@file:Suppress("unused")

package top.mckingdom.powerfulterritory.data

import org.bukkit.Chunk
import org.bukkit.Location
import org.kingdoms.constants.base.KeyedKingdomsObject
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.abstraction.data.DeserializationContext
import org.kingdoms.constants.land.abstraction.data.SerializationContext
import org.kingdoms.constants.land.location.SimpleChunkLocation
import org.kingdoms.constants.metadata.KingdomMetadata
import org.kingdoms.constants.metadata.KingdomMetadataHandler
import org.kingdoms.constants.namespace.Namespace
import org.kingdoms.data.database.dataprovider.SectionCreatableDataSetter
import org.kingdoms.data.database.dataprovider.SectionableDataGetter
import org.kingdoms.locale.Language
import org.kingdoms.locale.SupportedLanguage
import top.mckingdom.powerfulterritory.PowerfulTerritoryAddon
import top.mckingdom.powerfulterritory.util.PowerfulTerritoryLogger
import top.mckingdom.powerfulterritory.constants.land_categories.LandCategory
import top.mckingdom.powerfulterritory.constants.land_categories.StandardLandCategory

var SimpleChunkLocation.category: LandCategory?
    get() = this.land?.category
    set(category) {
        this.land?.category = category
    }

var Chunk.category: LandCategory?
    get() = Land.getLand(this)?.category
    set(category) {
        Land.getLand(this)?.category = category
    }

@JvmName("getCategory")
fun Location.getCategory(): LandCategory? {
    return (SimpleChunkLocation.of(this)).category
}

@get:JvmName("getCategory")
@set:JvmName("setCategory")
var Land.category: LandCategory?
    get() {
        if (this.isClaimed()) {
            return this.getCategoryRaw() ?: return StandardLandCategory.NONE
        } else {
            return null
        }
    }
    set(category) {
        if (category != null) {
            this.metadata.put(LandCategoryMetaHandler.INSTANCE, LandCategoryMeta(category))
        } else {
            this.metadata.remove(LandCategoryMetaHandler.INSTANCE)
        }
    }

/**
 * Get the raw category data from the [this] land metadata.
 */
fun Land.getCategoryRaw(): LandCategory? {
    return this.getMetadata().get(LandCategoryMetaHandler.INSTANCE)?.getValue() as? LandCategory
}

fun Land.clearCategoryData() {
    this.metadata.remove(LandCategoryMetaHandler.INSTANCE)
}

class LandCategoryMeta(private var landCategory: LandCategory) : KingdomMetadata {
    override fun getValue(): Any {
        return this.landCategory
    }

    override fun setValue(o: Any) {
        this.landCategory = o as LandCategory
    }

    override fun serialize(
        container: KeyedKingdomsObject<*>,
        context: SerializationContext<SectionCreatableDataSetter>
    ) {
        context.getDataProvider().setString(landCategory.getNamespace().asString())
    }

    override fun shouldSave(container: KeyedKingdomsObject<*>): Boolean {
        return container is Land
    }
}

class LandCategoryMetaHandler private constructor() :
    KingdomMetadataHandler(PowerfulTerritoryAddon.buildNS("LAND_CATEGORY")) {
    override fun deserialize(
        container: KeyedKingdomsObject<*>,
        dataGetter: DeserializationContext<SectionableDataGetter>
    ): KingdomMetadata {
        val chunkTypeNS = Namespace.fromString(dataGetter.getDataProvider().asString())
        val landCategory = PowerfulTerritoryAddon.get().getLandCategoryRegistry().getRegistered(chunkTypeNS)
        if (landCategory == null) {
            PowerfulTerritoryLogger.warn(
                "Unknown land category: " + dataGetter.getDataProvider().asString() + ", ignore it"
            )
            return LandCategoryMeta(StandardLandCategory.NONE)
        }
        return LandCategoryMeta(landCategory)
    }

    companion object {
        @JvmField
        val INSTANCE = LandCategoryMetaHandler()
    }
}

fun registerLandCategoryExternalMessageContextEdit() {
    top.mckingdom.auspice.util.land.addExtMessageContextEdit("category") { land -> land.category }
}

/**
 * For command
 */
object Categories {
    @JvmStatic
    fun initialize() {
        SupportedLanguage.entries.forEach { lang ->
            if (lang.isLoaded()) {
                categoriesString.put(lang, HashMap<String, LandCategory>().also {
                    PowerfulTerritoryAddon.get().getLandCategoryRegistry().getRegistry().forEach { _, category ->
                        it.put(category.getName(lang), category)
                    }
                })
            }
        }
    }

    @JvmField
    val categoriesString = HashMap<Language, HashMap<String, LandCategory>>()

    @JvmStatic
    fun getLandCategory(name: String, language: Language, containsNotEditable: Boolean): LandCategory? {
        val category = categoriesString.get(language)?.get(name)
        if (containsNotEditable) {
            return category
        } else {
            if (category?.isEditable() == true) {
                return category
            } else {
                return null
            }
        }
    }

    @JvmStatic
    fun getCategories(starts: String, language: Language, containsNotEditable: Boolean): List<String> {
        val out: MutableList<String> = ArrayList()
        categoriesString.get(language)?.values?.forEach { category: LandCategory ->
            if (containsNotEditable || category.isEditable()) {
                if (category.getName(language).startsWith(starts)) {
                    out.add(category.getName(language))
                }
            }
        }
        return out
    }
}
