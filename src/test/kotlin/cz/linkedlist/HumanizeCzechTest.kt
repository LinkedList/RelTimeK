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
class HumanizeCzechTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("cs-CZ"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, pár sekund",
        "SECONDS, 45, 45 sekund",
        "SECONDS, 48, minuta",
        "MINUTES, 1, minuta",
        "MINUTES, 3, 3 minuty",
        "MINUTES, 5, 5 minut",
        "MINUTES, 45, hodina",
        "HOURS, 1, hodina",
        "HOURS, 3, 3 hodiny",
        "HOURS, 5, 5 hodin",
        "HOURS, 22, den",
        "DAYS, 1, den",
        "DAYS, 3, 3 dny",
        "DAYS, 5, 5 dní",
        "DAYS, 26, měsíc",
        "MONTHS, 1, měsíc",
        "DAYS, 93, 3 měsíce",
        "DAYS, 341, rok",
        "YEARS, 1, rok",
        "YEARS, 4, 4 roky",
        "YEARS, 5, 5 let"
    ])
    fun testCzech(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("za 5 minut"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("před 5 minutami"))
    }
}