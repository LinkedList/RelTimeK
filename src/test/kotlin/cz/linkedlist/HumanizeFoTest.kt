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
class HumanizeFoTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("fo"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, fá sekund",
        "SECONDS, 45, 45 sekundir",
        "SECONDS, 48, ein minutt",
        "MINUTES, 1, ein minutt",
        "MINUTES, 3, 3 minuttir",
        "MINUTES, 45, ein tími",
        "HOURS, 1, ein tími",
        "HOURS, 3, 3 tímar",
        "HOURS, 22, ein dagur",
        "DAYS, 1, ein dagur",
        "DAYS, 3, 3 dagar",
        "DAYS, 26, ein mánaði",
        "MONTHS, 1, ein mánaði",
        "DAYS, 93, 3 mánaðir",
        "DAYS, 341, eitt ár",
        "YEARS, 1, eitt ár",
        "YEARS, 4, 4 ár"
    ])
    fun testFo(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("um 5 minuttir"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minuttir síðani"))
    }
}
