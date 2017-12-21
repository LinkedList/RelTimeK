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
class HumanizeViTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("vi"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, vài giây",
        "SECONDS, 45, 45 giây",
        "SECONDS, 48, một phút",
        "MINUTES, 1, một phút",
        "MINUTES, 3, 3 phút",
        "MINUTES, 45, một giờ",
        "HOURS, 1, một giờ",
        "HOURS, 3, 3 giờ",
        "HOURS, 22, một ngày",
        "DAYS, 1, một ngày",
        "DAYS, 3, 3 ngày",
        "DAYS, 26, một tháng",
        "MONTHS, 1, một tháng",
        "DAYS, 93, 3 tháng",
        "DAYS, 341, một năm",
        "YEARS, 1, một năm",
        "YEARS, 4, 4 năm"
    ])
    fun testVi(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 phút tới"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 phút trước"))
    }
}
