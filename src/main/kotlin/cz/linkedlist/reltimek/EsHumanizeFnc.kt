package cz.linkedlist.reltimek

import java.time.Duration

class EsHumanizeFnc: HumanizeFnc {

    private val relTime = mapOf(
            RelativeTime.mm to "un minuto"
    )
    override fun map(): Map<RelativeTime, String> {
        return relTime
    }

    override fun humanize(d: Duration): String {
        return "un minuto"
    }

}