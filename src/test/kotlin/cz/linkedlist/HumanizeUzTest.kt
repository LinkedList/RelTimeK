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
class HumanizeUzTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("uz"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, фурсат",
        "SECONDS, 45, 45 фурсат",
        "SECONDS, 48, бир дакика",
        "MINUTES, 1, бир дакика",
        "MINUTES, 3, 3 дакика",
        "MINUTES, 45, бир соат",
        "HOURS, 1, бир соат",
        "HOURS, 3, 3 соат",
        "HOURS, 22, бир кун",
        "DAYS, 1, бир кун",
        "DAYS, 3, 3 кун",
        "DAYS, 26, бир ой",
        "MONTHS, 1, бир ой",
        "DAYS, 93, 3 ой",
        "DAYS, 341, бир йил",
        "YEARS, 1, бир йил",
        "YEARS, 4, 4 йил"
    ])
    fun testUz(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("Якин 5 дакика ичида"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("Бир неча 5 дакика олдин"))
    }
}
