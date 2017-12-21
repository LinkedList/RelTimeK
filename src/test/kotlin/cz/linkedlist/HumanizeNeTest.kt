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
class HumanizeNeTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ne"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, केही क्षण",
        "SECONDS, 45, 45 सेकेण्ड",
        "SECONDS, 48, एक मिनेट",
        "MINUTES, 1, एक मिनेट",
        "MINUTES, 3, 3 मिनेट",
        "MINUTES, 45, एक घण्टा",
        "HOURS, 1, एक घण्टा",
        "HOURS, 3, 3 घण्टा",
        "HOURS, 22, एक दिन",
        "DAYS, 1, एक दिन",
        "DAYS, 3, 3 दिन",
        "DAYS, 26, एक महिना",
        "MONTHS, 1, एक महिना",
        "DAYS, 93, 3 महिना",
        "DAYS, 341, एक बर्ष",
        "YEARS, 1, एक बर्ष",
        "YEARS, 4, 4 बर्ष"
    ])
    fun testNe(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 मिनेटमा"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 मिनेट अगाडि"))
    }
}
