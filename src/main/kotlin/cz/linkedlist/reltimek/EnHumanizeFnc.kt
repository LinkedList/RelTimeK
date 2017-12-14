package cz.linkedlist.reltimek


class EnHumanizeFnc: SimpleFnc {
    override val future = "in %s"
    override val past = "%s ago"

    override val map = mapOf(
            RelativeTime.SECONDS.single to "a few seconds",
            RelativeTime.SECONDS.double to "%d seconds",
            RelativeTime.MINUTES.single to "a minute",
            RelativeTime.MINUTES.double to "%d minutes",
            RelativeTime.HOURS.single to "an hour",
            RelativeTime.HOURS.double to "%d hours",
            RelativeTime.DAYS.single to "a day",
            RelativeTime.DAYS.double to "%d days",
            RelativeTime.MONTHS.single to "a month",
            RelativeTime.MONTHS.double to "%d months",
            RelativeTime.YEARS.single to "a year",
            RelativeTime.YEARS.double to "%d years"
    )
}