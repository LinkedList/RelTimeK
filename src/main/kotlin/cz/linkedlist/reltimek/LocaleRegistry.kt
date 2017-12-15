package cz.linkedlist.reltimek

import cz.linkedlist.reltimek.functions.CsHumanizeFnc
import cz.linkedlist.reltimek.functions.EnHumanizeFnc
import cz.linkedlist.reltimek.functions.EsHumanizeFnc
import java.util.*
import kotlin.collections.HashMap

class LocaleRegistry {

    private val localeRegistry: Map<Locale, HumanizeFnc>

    init {
        val tempRegistry = mutableMapOf<Locale, HumanizeFnc>()
        tempRegistry[Locale.forLanguageTag("cs_CZ")] = CsHumanizeFnc()
        tempRegistry[Locale.forLanguageTag("es")] = EsHumanizeFnc()
        tempRegistry[Locale.US] = EnHumanizeFnc()

        localeRegistry = HashMap(tempRegistry)
    }

    fun getForLocale(locale: Locale): HumanizeFnc {
        return localeRegistry.getOrDefault(locale, EnHumanizeFnc())
    }

}

