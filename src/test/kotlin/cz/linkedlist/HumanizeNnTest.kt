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
class HumanizeNnTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("nn"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, nokre sekund",
        "SECONDS, 45, 45 sekund",
        "SECONDS, 48, eit minutt",
        "MINUTES, 1, eit minutt",
        "MINUTES, 3, 3 minutt",
        "MINUTES, 45, ein time",
        "HOURS, 1, ein time",
        "HOURS, 3, 3 timar",
        "HOURS, 22, ein dag",
        "DAYS, 1, ein dag",
        "DAYS, 3, 3 dagar",
        "DAYS, 26, ein månad",
        "MONTHS, 1, ein månad",
        "DAYS, 93, 3 månader",
        "DAYS, 341, eit år",
        "YEARS, 1, eit år",
        "YEARS, 4, 4 år"
    ])
    fun testNn(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("om 5 minutt"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minutt sidan"))
    }
}
