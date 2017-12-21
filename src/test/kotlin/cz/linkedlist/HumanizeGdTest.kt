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
class HumanizeGdTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("gd"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, beagan diogan",
        "SECONDS, 45, 45 diogan",
        "SECONDS, 48, mionaid",
        "MINUTES, 1, mionaid",
        "MINUTES, 3, 3 mionaidean",
        "MINUTES, 45, uair",
        "HOURS, 1, uair",
        "HOURS, 3, 3 uairean",
        "HOURS, 22, latha",
        "DAYS, 1, latha",
        "DAYS, 3, 3 latha",
        "DAYS, 26, mìos",
        "MONTHS, 1, mìos",
        "DAYS, 93, 3 mìosan",
        "DAYS, 341, bliadhna",
        "YEARS, 1, bliadhna",
        "YEARS, 4, 4 bliadhna"
    ])
    fun testGd(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("ann an 5 mionaidean"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("bho chionn 5 mionaidean"))
    }
}
