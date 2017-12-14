package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
class HumanizeEnglishTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.US)
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, a few seconds",
        "SECONDS, 45, 45 seconds",
        "SECONDS, 48, a minute",
        "MINUTES, 1, a minute",
        "MINUTES, 3, 3 minutes",
        "MINUTES, 45, an hour",
        "HOURS, 1, an hour",
        "HOURS, 3, 3 hours",
        "HOURS, 22, a day",
        "DAYS, 1, a day",
        "DAYS, 3, 3 days",
        "DAYS, 26, a month",
        "MONTHS, 1, a month",
        "DAYS, 93, 3 months",
        "DAYS, 341, a year",
        "YEARS, 1, a year",
        "YEARS, 4, 4 years"
    ])
    fun testEnglish(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val before = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, before).humanize(), equalTo(result))
    }

}