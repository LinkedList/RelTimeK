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
class HumanizeEuTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("eu"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, segundo batzuk",
        "SECONDS, 45, 45 segundo",
        "SECONDS, 48, minutu bat",
        "MINUTES, 1, minutu bat",
        "MINUTES, 3, 3 minutu",
        "MINUTES, 45, ordu bat",
        "HOURS, 1, ordu bat",
        "HOURS, 3, 3 ordu",
        "HOURS, 22, egun bat",
        "DAYS, 1, egun bat",
        "DAYS, 3, 3 egun",
        "DAYS, 26, hilabete bat",
        "MONTHS, 1, hilabete bat",
        "DAYS, 93, 3 hilabete",
        "DAYS, 341, urte bat",
        "YEARS, 1, urte bat",
        "YEARS, 4, 4 urte"
    ])
    fun testEu(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minutu barru"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("duela 5 minutu"))
    }
}
