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
class HumanizeZhTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("zh"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, 幾秒",
        "SECONDS, 45, 45 秒",
        "SECONDS, 48, 1 分鐘",
        "MINUTES, 1, 1 分鐘",
        "MINUTES, 3, 3 分鐘",
        "MINUTES, 45, 1 小時",
        "HOURS, 1, 1 小時",
        "HOURS, 3, 3 小時",
        "HOURS, 22, 1 天",
        "DAYS, 1, 1 天",
        "DAYS, 3, 3 天",
        "DAYS, 26, 1 個月",
        "MONTHS, 1, 1 個月",
        "DAYS, 93, 3 個月",
        "DAYS, 341, 1 年",
        "YEARS, 1, 1 年",
        "YEARS, 4, 4 年"
    ])
    fun testZh(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 分鐘內"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 分鐘前"))
    }
}
