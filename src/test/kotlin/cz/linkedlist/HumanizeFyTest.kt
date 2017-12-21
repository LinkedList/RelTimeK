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
class HumanizeFyTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("fy"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, in pear sekonden",
        "SECONDS, 45, 45 sekonden",
        "SECONDS, 48, ien minút",
        "MINUTES, 1, ien minút",
        "MINUTES, 3, 3 minuten",
        "MINUTES, 45, ien oere",
        "HOURS, 1, ien oere",
        "HOURS, 3, 3 oeren",
        "HOURS, 22, ien dei",
        "DAYS, 1, ien dei",
        "DAYS, 3, 3 dagen",
        "DAYS, 26, ien moanne",
        "MONTHS, 1, ien moanne",
        "DAYS, 93, 3 moannen",
        "DAYS, 341, ien jier",
        "YEARS, 1, ien jier",
        "YEARS, 4, 4 jierren"
    ])
    fun testFy(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("oer 5 minuten"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minuten lyn"))
    }
}
