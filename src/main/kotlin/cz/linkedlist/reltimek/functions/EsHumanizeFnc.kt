package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.RelativeTime
import cz.linkedlist.reltimek.SimpleFnc

@RegisterFnc("es")
class EsHumanizeFnc: SimpleFnc {
    override val future = "en %s"
    override val past = "hace %s"

    override val map = mapOf(
            RelativeTime.SECONDS.single to "pocos segundos",
            RelativeTime.SECONDS.double to "%d sequndos",
            RelativeTime.MINUTES.single to "un minuto",
            RelativeTime.MINUTES.double to "%d minutos",
            RelativeTime.HOURS.single to "una hora",
            RelativeTime.HOURS.double to "%d horas",
            RelativeTime.DAYS.single to "un día",
            RelativeTime.DAYS.double to "%d días",
            RelativeTime.MONTHS.single to "un mes",
            RelativeTime.MONTHS.double to "%d meses",
            RelativeTime.YEARS.single to "un año",
            RelativeTime.YEARS.double to "%d años"
    )
}