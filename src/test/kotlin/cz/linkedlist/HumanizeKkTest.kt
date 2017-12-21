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
class HumanizeKkTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("kk"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, бірнеше секунд",
        "SECONDS, 45, 45 секунд",
        "SECONDS, 48, бір минут",
        "MINUTES, 1, бір минут",
        "MINUTES, 3, 3 минут",
        "MINUTES, 45, бір сағат",
        "HOURS, 1, бір сағат",
        "HOURS, 3, 3 сағат",
        "HOURS, 22, бір күн",
        "DAYS, 1, бір күн",
        "DAYS, 3, 3 күн",
        "DAYS, 26, бір ай",
        "MONTHS, 1, бір ай",
        "DAYS, 93, 3 ай",
        "DAYS, 341, бір жыл",
        "YEARS, 1, бір жыл",
        "YEARS, 4, 4 жыл"
    ])
    fun testKk(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 минут ішінде"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 минут бұрын"))
    }
}
