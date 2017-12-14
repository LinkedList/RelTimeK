package cz.linkedlist.reltimek

class EsHumanizeFnc: SimpleFnc {
    override val map: Map<String, String>
        get() = mapOf(
            RelativeTime.MINUTES.single to "un minuto",
            RelativeTime.MINUTES.double to "%d minutos"
    )
}