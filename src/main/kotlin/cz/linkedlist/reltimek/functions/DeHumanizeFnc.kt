package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.ComplexFnc
import cz.linkedlist.reltimek.Payload
import cz.linkedlist.reltimek.RelativeTime

@RegisterFnc("de")
class DeHumanizeFnc : ComplexFnc {
    override val future = "in %s"
    override val past = "vor %s"

    override fun processPayload(payload: Payload): String {
        val withoutSuffix = payload.withoutSuffix

        return if(payload.double) {
            val num = payload.num
            when(payload.relTime) {
                RelativeTime.SECONDS -> "$num Sekunden"
                RelativeTime.MINUTES -> "$num Minuten"
                RelativeTime.HOURS -> "$num Stunden"
                RelativeTime.DAYS -> "$num " + format(withoutSuffix, "Tage", "Tagen")
                RelativeTime.MONTHS -> "$num " + format(withoutSuffix, "Monate", "Monaten")
                RelativeTime.YEARS -> "$num " + format(withoutSuffix, "Jahre", "Jahren")
            }
        } else {
           when(payload.relTime)  {
               RelativeTime.SECONDS -> "ein paar Sekunden"
               RelativeTime.MINUTES -> format(withoutSuffix, "eine Minute", "einer Minute")
               RelativeTime.HOURS -> format(withoutSuffix, "eine Stunde", "einer Stunde")
               RelativeTime.DAYS -> format(withoutSuffix, "ein Tag", "einem Tag")
               RelativeTime.MONTHS -> format(withoutSuffix, "ein Monat", "einem Monat")
               RelativeTime.YEARS -> format(withoutSuffix, "ein Jahr", "einem Jahr")
           }
        }
    }

    private fun format(withoutSuffix: Boolean, without: String, with: String): String {
        return if(withoutSuffix) without else with
    }
}