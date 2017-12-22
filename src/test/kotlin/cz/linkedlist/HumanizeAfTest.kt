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
class HumanizeAfTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("af"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, '''n paar sekondes'",
        "SECONDS, 45, 45 sekondes",
        "SECONDS, 48, '''n minuut'",
        "MINUTES, 1, '''n minuut'",
        "MINUTES, 3, 3 minute",
        "MINUTES, 45, '''n uur'",
        "HOURS, 1, '''n uur'",
        "HOURS, 3, 3 ure",
        "HOURS, 22, '''n dag'",
        "DAYS, 1, '''n dag'",
        "DAYS, 3, 3 dae",
        "DAYS, 26, '''n maand'",
        "MONTHS, 1, '''n maand'",
        "DAYS, 93, 3 maande",
        "DAYS, 341, '''n jaar'",
        "YEARS, 1, '''n jaar'",
        "YEARS, 4, 4 jaar"
    ])
    fun testAf(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("oor 5 minute"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minute gelede"))
    }
}
