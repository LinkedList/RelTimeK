package cz.linkedlist.reltimek

import cz.linkedlist.reltimek.functions.CsHumanizeFnc
import cz.linkedlist.reltimek.functions.EnHumanizeFnc
import cz.linkedlist.reltimek.functions.RegisterFnc
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import java.io.File
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

        val cl = ClassLoader.getSystemClassLoader()
        val csv = File(cl.getResource("simple.csv").file)
        csv.forEachLine {
            val pair = lineToInstance(it)
            tempRegistry[Locale.forLanguageTag(pair.first)] = pair.second
        }

        localeRegistry = HashMap(tempRegistry)
    }

    fun getForLocale(locale: Locale): HumanizeFnc {
        return if(locale.script.isEmpty()) {
            localeRegistry.getOrDefault(Locale.forLanguageTag(locale.language), EnHumanizeFnc())
        } else {
            localeRegistry.getOrDefault(locale, EnHumanizeFnc())
        }
    }

    private fun lineToInstance(line: String): Pair<String, SimpleFncImpl> {
        val arr = line.split(",")
        val map = mapOf(
            RelativeTime.SECONDS.single to arr[3],
            RelativeTime.SECONDS.double to arr[4],
            RelativeTime.MINUTES.single to arr[5],
            RelativeTime.MINUTES.double to arr[6],
            RelativeTime.HOURS.single to arr[7],
            RelativeTime.HOURS.double to arr[8],
            RelativeTime.DAYS.single to arr[9],
            RelativeTime.DAYS.double to arr[10],
            RelativeTime.MONTHS.single to arr[11],
            RelativeTime.MONTHS.double to arr[12],
            RelativeTime.YEARS.single to arr[13],
            RelativeTime.YEARS.double to arr[14]
        )

        val instance = SimpleFncImpl(arr[1], arr[2], map)

        return Pair(arr[0], instance)
    }

}

