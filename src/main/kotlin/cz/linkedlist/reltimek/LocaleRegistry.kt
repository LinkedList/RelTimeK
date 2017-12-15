package cz.linkedlist.reltimek

import cz.linkedlist.reltimek.functions.CsHumanizeFnc
import cz.linkedlist.reltimek.functions.EnHumanizeFnc
import cz.linkedlist.reltimek.functions.RegisterFnc
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import java.util.*
import kotlin.collections.HashMap

class LocaleRegistry {

    private val localeRegistry: Map<Locale, HumanizeFnc>

    init {
        val tempRegistry = mutableMapOf<Locale, HumanizeFnc>()
        FastClasspathScanner(CsHumanizeFnc::class.java.`package`.name)
                .matchClassesWithAnnotation(RegisterFnc::class.java, {
                    val annot = it.getDeclaredAnnotation(RegisterFnc::class.java)
                    tempRegistry[Locale.forLanguageTag(annot.language)] = it.newInstance() as HumanizeFnc
                })
                .disableRecursiveScanning()
                .scan()

        localeRegistry = HashMap(tempRegistry)
    }

    fun getForLocale(locale: Locale): HumanizeFnc {
        return localeRegistry.getOrDefault(Locale.forLanguageTag(locale.language), EnHumanizeFnc())
    }

}

