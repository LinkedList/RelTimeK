package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
class HumanizeFrTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("fr"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, quelques secondes",
        "SECONDS, 45, 45 secondes",
        "SECONDS, 48, une minute",
        "MINUTES, 1, une minute",
        "MINUTES, 3, 3 minutes",
        "MINUTES, 45, une heure",
        "HOURS, 1, une heure",
        "HOURS, 3, 3 heures",
        "HOURS, 22, un jour",
        "DAYS, 1, un jour",
        "DAYS, 3, 3 jours",
        "DAYS, 26, un mois",
        "MONTHS, 1, un mois",
        "DAYS, 93, 3 mois",
        "DAYS, 341, un an",
        "YEARS, 1, un an",
        "YEARS, 4, 4 ans"
    ])
    fun testFr(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val before = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, before).humanize(), equalTo(result))
    }

    @Test
    fun testFuture() {
        val now = LocalDateTime.now()
        val before = now.plusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("dans 5 minutes"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("il y a 5 minutes"))
    }
}
