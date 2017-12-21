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
class HumanizePtTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("pt"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, segundos",
        "SECONDS, 45, 45 segundos",
        "SECONDS, 48, um minuto",
        "MINUTES, 1, um minuto",
        "MINUTES, 3, 3 minutos",
        "MINUTES, 45, uma hora",
        "HOURS, 1, uma hora",
        "HOURS, 3, 3 horas",
        "HOURS, 22, um dia",
        "DAYS, 1, um dia",
        "DAYS, 3, 3 dias",
        "DAYS, 26, um mês",
        "MONTHS, 1, um mês",
        "DAYS, 93, 3 meses",
        "DAYS, 341, um ano",
        "YEARS, 1, um ano",
        "YEARS, 4, 4 anos"
    ])
    fun testPt(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("em 5 minutos"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("há 5 minutos"))
    }
}
