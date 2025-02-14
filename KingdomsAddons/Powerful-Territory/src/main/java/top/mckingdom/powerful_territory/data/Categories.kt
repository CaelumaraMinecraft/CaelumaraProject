@file:JvmName("LandCategories")
@file:Suppress("unused")

package top.mckingdom.powerful_territory.data

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
import top.mckingdom.powerful_territory.PowerfulTerritory
import top.mckingdom.powerful_territory.constants.land_categories.LandCategory
import top.mckingdom.powerful_territory.constants.land_categories.StandardLandCategory
import top.mckingdom.powerful_territory.PowerfulTerritoryLogger

@JvmName("setCategory")
fun Chunk.setCategory(landCategory: LandCategory) {
    Land.getLand(this)?.setCategory(landCategory)
}

@JvmName("setCategory")
fun SimpleChunkLocation.setCategory(landCategory: LandCategory) {
    this.getLand()?.setCategory(landCategory)
}

@JvmName("setCategory")
fun Land.setCategory(landCategory: LandCategory) {
    this.getMetadata().put(LandCategoryMetaHandler.INSTANCE, LandCategoryMeta(landCategory))
}

@JvmName("getCategory")
fun Location.getCategory(): LandCategory? {
    return (SimpleChunkLocation.of(this)).getCategory()
}

@JvmName("getCategory")
fun Chunk.getCategory(): LandCategory? {
    return SimpleChunkLocation.of(this).land?.getCategory()
}

@JvmName("getCategory")
fun SimpleChunkLocation.getCategory(): LandCategory? {
    return this.land?.getCategory()
}


/**
 * Get the category of a constants
 * If this constants is not claimed, it will also return null
 * If this constants is claimed, it will return a not null value
 */
@JvmName("getCategory")
fun Land.getCategory(): LandCategory? {
    if (this.isClaimed()) {
        return this.getCategoryData() ?: return StandardLandCategory.NONE
    } else {
        return null
    }
}


/**
 * Get the data from the metadata,
 * You shouldn't use it except you know how to use this function
 */
@JvmName("getCategoryData")
fun Land.getCategoryData(): LandCategory? {
    return this.getMetadata().get(LandCategoryMetaHandler.INSTANCE)?.getValue() as? LandCategory
}


@JvmName("clearCategoryData")
fun Land.clearCategoryData() {
    this.metadata.remove(LandCategoryMetaHandler.INSTANCE)
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
                    PowerfulTerritory.get().getLandCategoryRegistry().getRegistry().forEach { _, category ->
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
    KingdomMetadataHandler(Namespace("PowerfulTerritory", "LAND_CATEGORY")) {
    override fun deserialize(
        container: KeyedKingdomsObject<*>,
        dataGetter: DeserializationContext<SectionableDataGetter>
    ): KingdomMetadata {
        val chunkTypeNS = Namespace.fromString(dataGetter.getDataProvider().asString())
        val landCategory = PowerfulTerritory.get().getLandCategoryRegistry().getRegistered(chunkTypeNS)
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
