package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.ComplexFnc
import cz.linkedlist.reltimek.Payload
import cz.linkedlist.reltimek.RelativeTime
import cz.linkedlist.reltimek.SimpleFncImpl

@RegisterFnc("gl")
class GlHumanizeFnc : ComplexFnc{

    private val simple = SimpleFncImpl(null, null, mapOf(
            RelativeTime.SECONDS.single to "uns segundos",
            RelativeTime.SECONDS.double to "%d segundos",
            RelativeTime.MINUTES.single to "un minuto",
            RelativeTime.MINUTES.double to "%d minutos",
            RelativeTime.HOURS.single to "unha hora",
            RelativeTime.HOURS.double to "%d horas",
            RelativeTime.DAYS.single to "un día",
            RelativeTime.DAYS.double to "%d días",
            RelativeTime.MONTHS.single to "un mes",
            RelativeTime.MONTHS.double to "%d meses",
            RelativeTime.YEARS.single to "un ano",
            RelativeTime.YEARS.double to "%d anos"
        )
    )
    override val future = null
    override val past = null

    override fun future(processed: String, payload: Payload): String {
        return if(processed.startsWith("un")) {
            "n$processed"
        } else {
            "en $processed"
        }
    }

    override fun past(processed: String, payload: Payload): String {
        return "hai $processed"
    }

    override fun processPayload(payload: Payload): String {
        return simple.humanize(payload.copy(withoutSuffix = true))
    }
}
