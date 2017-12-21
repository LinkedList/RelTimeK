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
class HumanizeJvTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("jv"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, sawetawis detik",
        "SECONDS, 45, 45 detik",
        "SECONDS, 48, setunggal menit",
        "MINUTES, 1, setunggal menit",
        "MINUTES, 3, 3 menit",
        "MINUTES, 45, setunggal jam",
        "HOURS, 1, setunggal jam",
        "HOURS, 3, 3 jam",
        "HOURS, 22, sedinten",
        "DAYS, 1, sedinten",
        "DAYS, 3, 3 dinten",
        "DAYS, 26, sewulan",
        "MONTHS, 1, sewulan",
        "DAYS, 93, 3 wulan",
        "DAYS, 341, setaun",
        "YEARS, 1, setaun",
        "YEARS, 4, 4 taun"
    ])
    fun testJv(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("wonten ing 5 menit"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 menit ingkang kepengker"))
    }
}
