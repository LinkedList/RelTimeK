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
class HumanizeKoTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ko"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, 몇 초",
        "SECONDS, 45, 45초",
        "SECONDS, 48, 1분",
        "MINUTES, 1, 1분",
        "MINUTES, 3, 3분",
        "MINUTES, 45, 한 시간",
        "HOURS, 1, 한 시간",
        "HOURS, 3, 3시간",
        "HOURS, 22, 하루",
        "DAYS, 1, 하루",
        "DAYS, 3, 3일",
        "DAYS, 26, 한 달",
        "MONTHS, 1, 한 달",
        "DAYS, 93, 3달",
        "DAYS, 341, 일 년",
        "YEARS, 1, 일 년",
        "YEARS, 4, 4년"
    ])
    fun testKo(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5분 후"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5분 전"))
    }
}
