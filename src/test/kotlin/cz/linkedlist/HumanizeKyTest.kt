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
class HumanizeKyTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ky"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, бирнече секунд",
        "SECONDS, 45, 45 секунд",
        "SECONDS, 48, бир мүнөт",
        "MINUTES, 1, бир мүнөт",
        "MINUTES, 3, 3 мүнөт",
        "MINUTES, 45, бир саат",
        "HOURS, 1, бир саат",
        "HOURS, 3, 3 саат",
        "HOURS, 22, бир күн",
        "DAYS, 1, бир күн",
        "DAYS, 3, 3 күн",
        "DAYS, 26, бир ай",
        "MONTHS, 1, бир ай",
        "DAYS, 93, 3 ай",
        "DAYS, 341, бир жыл",
        "YEARS, 1, бир жыл",
        "YEARS, 4, 4 жыл"
    ])
    fun testKy(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 мүнөт ичинде"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 мүнөт мурун"))
    }
}
