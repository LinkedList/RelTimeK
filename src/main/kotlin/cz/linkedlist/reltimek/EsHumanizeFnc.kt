package cz.linkedlist.reltimek

import java.time.Duration

class EsHumanizeFnc: HumanizeFnc {

    private val relTime = mapOf(
            RelativeTime.MINUTES.single to "un minuto",
            RelativeTime.MINUTES.double to "%d minutos"
    )
    override fun map(): Map<String, String> {
        return relTime
    }

    override fun humanize(d: Duration): String {
        return "un minuto"
    }

}