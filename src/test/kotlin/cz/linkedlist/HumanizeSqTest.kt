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
class HumanizeSqTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("sq"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, disa sekonda",
        "SECONDS, 45, 45 sekonda",
        "SECONDS, 48, një minutë",
        "MINUTES, 1, një minutë",
        "MINUTES, 3, 3 minuta",
        "MINUTES, 45, një orë",
        "HOURS, 1, një orë",
        "HOURS, 3, 3 orë",
        "HOURS, 22, një ditë",
        "DAYS, 1, një ditë",
        "DAYS, 3, 3 ditë",
        "DAYS, 26, një muaj",
        "MONTHS, 1, një muaj",
        "DAYS, 93, 3 muaj",
        "DAYS, 341, një vit",
        "YEARS, 1, një vit",
        "YEARS, 4, 4 vite"
    ])
    fun testSq(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("në 5 minuta"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minuta më parë"))
    }
}
