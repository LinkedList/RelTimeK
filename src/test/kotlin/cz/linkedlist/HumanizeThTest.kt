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
class HumanizeThTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("th"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ไม่กี่วินาที",
        "SECONDS, 45, 45 วินาที",
        "SECONDS, 48, 1 นาที",
        "MINUTES, 1, 1 นาที",
        "MINUTES, 3, 3 นาที",
        "MINUTES, 45, 1 ชั่วโมง",
        "HOURS, 1, 1 ชั่วโมง",
        "HOURS, 3, 3 ชั่วโมง",
        "HOURS, 22, 1 วัน",
        "DAYS, 1, 1 วัน",
        "DAYS, 3, 3 วัน",
        "DAYS, 26, 1 เดือน",
        "MONTHS, 1, 1 เดือน",
        "DAYS, 93, 3 เดือน",
        "DAYS, 341, 1 ปี",
        "YEARS, 1, 1 ปี",
        "YEARS, 4, 4 ปี"
    ])
    fun testTh(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("อีก 5 นาที"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 นาทีที่แล้ว"))
    }
}
