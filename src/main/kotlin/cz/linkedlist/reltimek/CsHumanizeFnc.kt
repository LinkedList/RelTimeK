package cz.linkedlist.reltimek

class CsHumanizeFnc : SimpleFnc {
    override val future = "za %s"
    override val past = " před %s"

    override val map = mapOf(
            RelativeTime.SECONDS.single to "pár sekund",
            RelativeTime.SECONDS.double to "%d sekund",
            RelativeTime.MINUTES.single to "minuta",
            RelativeTime.MINUTES.double to "%d minut",
            RelativeTime.HOURS.single to "hodina"
        )
}