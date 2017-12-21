package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.ComplexFnc
import cz.linkedlist.reltimek.Payload
import cz.linkedlist.reltimek.RelativeTime
import cz.linkedlist.reltimek.SimpleFncImpl

@RegisterFnc("it")
class ItHumanizeFnc: ComplexFnc{

    private val futurePattern = Regex("^[0-9](.+)")
    private val simple = SimpleFncImpl(null, null, mapOf(
            RelativeTime.SECONDS.single to "alcuni secondi",
            RelativeTime.SECONDS.double to "%d secondi",
            RelativeTime.MINUTES.single to "un minuto",
            RelativeTime.MINUTES.double to "%d minuti",
            RelativeTime.HOURS.single to "un'ora",
            RelativeTime.HOURS.double to "%d ore",
            RelativeTime.DAYS.single to "un giorno",
            RelativeTime.DAYS.double to "%d giorni",
            RelativeTime.MONTHS.single to "un mese",
            RelativeTime.MONTHS.double to "%d mesi",
            RelativeTime.YEARS.single to "un anno",
            RelativeTime.YEARS.double to "%d anni"
        )
    )

    override val future = null
    override val past = null

    override fun future(processed: String, payload: Payload): String {
        return if(processed.matches(futurePattern)) {
            "tra $processed"
        } else {
            "in $processed"
        }
    }

    override fun past(processed: String, payload: Payload): String {
        return "$processed fa"
    }

    override fun processPayload(payload: Payload): String {
        return simple.humanize(payload.copy(withoutSuffix = true))
    }
}
