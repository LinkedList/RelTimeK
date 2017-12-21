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
class HumanizeUrTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ur"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, چند سیکنڈ",
        "SECONDS, 45, 45 سیکنڈ",
        "SECONDS, 48, ایک منٹ",
        "MINUTES, 1, ایک منٹ",
        "MINUTES, 3, 3 منٹ",
        "MINUTES, 45, ایک گھنٹہ",
        "HOURS, 1, ایک گھنٹہ",
        "HOURS, 3, 3 گھنٹے",
        "HOURS, 22, ایک دن",
        "DAYS, 1, ایک دن",
        "DAYS, 3, 3 دن",
        "DAYS, 26, ایک ماہ",
        "MONTHS, 1, ایک ماہ",
        "DAYS, 93, 3 ماہ",
        "DAYS, 341, ایک سال",
        "YEARS, 1, ایک سال",
        "YEARS, 4, 4 سال"
    ])
    fun testUr(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 منٹ بعد"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 منٹ قبل"))
    }
}
