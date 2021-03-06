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
class HumanizeHiTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("hi"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, कुछ ही क्षण",
        "SECONDS, 45, 45 सेकंड",
        "SECONDS, 48, एक मिनट",
        "MINUTES, 1, एक मिनट",
        "MINUTES, 3, 3 मिनट",
        "MINUTES, 45, एक घंटा",
        "HOURS, 1, एक घंटा",
        "HOURS, 3, 3 घंटे",
        "HOURS, 22, एक दिन",
        "DAYS, 1, एक दिन",
        "DAYS, 3, 3 दिन",
        "DAYS, 26, एक महीने",
        "MONTHS, 1, एक महीने",
        "DAYS, 93, 3 महीने",
        "DAYS, 341, एक वर्ष",
        "YEARS, 1, एक वर्ष",
        "YEARS, 4, 4 वर्ष"
    ])
    fun testHi(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 मिनट में"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 मिनट पहले"))
    }
}
