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
class HumanizeBmTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("bm"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, sanga dama dama",
        "SECONDS, 45, sekondi 45",
        "SECONDS, 48, miniti kelen",
        "MINUTES, 1, miniti kelen",
        "MINUTES, 3, miniti 3",
        "MINUTES, 45, lɛrɛ kelen",
        "HOURS, 1, lɛrɛ kelen",
        "HOURS, 3, lɛrɛ 3",
        "HOURS, 22, tile kelen",
        "DAYS, 1, tile kelen",
        "DAYS, 3, tile 3",
        "DAYS, 26, kalo kelen",
        "MONTHS, 1, kalo kelen",
        "DAYS, 93, kalo 3",
        "DAYS, 341, san kelen",
        "YEARS, 1, san kelen",
        "YEARS, 4, san 4"
    ])
    fun testBm(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("miniti 5 kɔnɔ"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("a bɛ miniti 5 bɔ"))
    }
}
