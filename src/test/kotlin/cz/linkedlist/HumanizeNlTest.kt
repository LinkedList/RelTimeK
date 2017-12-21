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
class HumanizeNlTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("nl"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, een paar seconden",
        "SECONDS, 45, 45 seconden",
        "SECONDS, 48, één minuut",
        "MINUTES, 1, één minuut",
        "MINUTES, 3, 3 minuten",
        "MINUTES, 45, één uur",
        "HOURS, 1, één uur",
        "HOURS, 3, 3 uur",
        "HOURS, 22, één dag",
        "DAYS, 1, één dag",
        "DAYS, 3, 3 dagen",
        "DAYS, 26, één maand",
        "MONTHS, 1, één maand",
        "DAYS, 93, 3 maanden",
        "DAYS, 341, één jaar",
        "YEARS, 1, één jaar",
        "YEARS, 4, 4 jaar"
    ])
    fun testNl(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("over 5 minuten"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minuten geleden"))
    }
}
