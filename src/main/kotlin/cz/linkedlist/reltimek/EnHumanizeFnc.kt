package cz.linkedlist.reltimek


class EnHumanizeFnc: SimpleFnc {
    override val map: Map<String, String>
        get() = mapOf(
                    RelativeTime.SECONDS.single to "a few seconds",
                    RelativeTime.SECONDS.double to "%d seconds",
                    RelativeTime.MINUTES.single to "a minute",
                    RelativeTime.MINUTES.double to "%d minutes"
            )
}