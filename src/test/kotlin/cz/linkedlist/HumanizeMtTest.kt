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
class HumanizeMtTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("mt"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ftit sekondi",
        "SECONDS, 45, 45 sekondi",
        "SECONDS, 48, minuta",
        "MINUTES, 1, minuta",
        "MINUTES, 3, 3 minuti",
        "MINUTES, 45, siegħa",
        "HOURS, 1, siegħa",
        "HOURS, 3, 3 siegħat",
        "HOURS, 22, ġurnata",
        "DAYS, 1, ġurnata",
        "DAYS, 3, 3 ġranet",
        "DAYS, 26, xahar",
        "MONTHS, 1, xahar",
        "DAYS, 93, 3 xhur",
        "DAYS, 341, sena",
        "YEARS, 1, sena",
        "YEARS, 4, 4 sni"
    ])
    fun testMt(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("f’ 5 minuti"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minuti ilu"))
    }
}
