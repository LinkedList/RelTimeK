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
class HumanizeFaTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("fa"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, چند ثانیه",
        "SECONDS, 45, ثانیه d%",
        "SECONDS, 48, یک دقیقه",
        "MINUTES, 1, یک دقیقه",
        "MINUTES, 3, 3 دقیقه",
        "MINUTES, 45, یک ساعت",
        "HOURS, 1, یک ساعت",
        "HOURS, 3, 3 ساعت",
        "HOURS, 22, یک روز",
        "DAYS, 1, یک روز",
        "DAYS, 3, 3 روز",
        "DAYS, 26, یک ماه",
        "MONTHS, 1, یک ماه",
        "DAYS, 93, 3 ماه",
        "DAYS, 341, یک سال",
        "YEARS, 1, یک سال",
        "YEARS, 4, 4 سال"
    ])
    fun testFa(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("در 5 دقیقه"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 دقیقه پیش"))
    }
}
