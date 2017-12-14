package cz.linkedlist.reltimek

class CsHumanizeFnc : SimpleFnc {
    override val map: Map<String, String>
        get() = mapOf(
            RelativeTime.SECONDS.single to "pár sekund",
            RelativeTime.SECONDS.double to "%d sekund",
            RelativeTime.MINUTES.single to "minuta",
            RelativeTime.MINUTES.double to "%d minut",
            RelativeTime.HOURS.single to "hodina"
        )
}