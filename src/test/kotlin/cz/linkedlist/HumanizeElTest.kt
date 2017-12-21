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
class HumanizeElTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("el"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, λίγα δευτερόλεπτα",
        "SECONDS, 45, 45 δευτερόλεπτα",
        "SECONDS, 48, ένα λεπτό",
        "MINUTES, 1, ένα λεπτό",
        "MINUTES, 3, 3 λεπτά",
        "MINUTES, 45, μία ώρα",
        "HOURS, 1, μία ώρα",
        "HOURS, 3, 3 ώρες",
        "HOURS, 22, μία μέρα",
        "DAYS, 1, μία μέρα",
        "DAYS, 3, 3 μέρες",
        "DAYS, 26, ένας μήνας",
        "MONTHS, 1, ένας μήνας",
        "DAYS, 93, 3 μήνες",
        "DAYS, 341, ένας χρόνος",
        "YEARS, 1, ένας χρόνος",
        "YEARS, 4, 4 χρόνια"
    ])
    fun testEl(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("σε 5 λεπτά"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 λεπτά πριν"))
    }
}
