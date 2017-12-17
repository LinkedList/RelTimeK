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
class HumanizeSwedishTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("sv"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, några sekunder",
        "SECONDS, 45, 45 sekunder",
        "SECONDS, 48, en minut",
        "MINUTES, 1, en minut",
        "MINUTES, 3, 3 minuter",
        "MINUTES, 45, en timme",
        "HOURS, 1, en timme",
        "HOURS, 3, 3 timmar",
        "HOURS, 22, en dag",
        "DAYS, 1, en dag",
        "DAYS, 3, 3 dagar",
        "DAYS, 26, en månad",
        "MONTHS, 1, en månad",
        "DAYS, 93, 3 månader",
        "DAYS, 341, ett år",
        "YEARS, 1, ett år",
        "YEARS, 4, 4 år"
    ])
    fun testDanish(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("om 5 minuter"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("för 5 minuter sedan"))
    }
}