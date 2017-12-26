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

class HumanizeGlTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("gl"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, uns segundos",
        "SECONDS, 45, 45 segundos",
        "SECONDS, 48, un minuto",
        "MINUTES, 1, un minuto",
        "MINUTES, 3, 3 minutos",
        "MINUTES, 45, unha hora",
        "HOURS, 1, unha hora",
        "HOURS, 3, 3 horas",
        "HOURS, 22, un día",
        "DAYS, 1, un día",
        "DAYS, 3, 3 días",
        "DAYS, 26, un mes",
        "MONTHS, 1, un mes",
        "DAYS, 93, 3 meses",
        "DAYS, 341, un ano",
        "YEARS, 1, un ano",
        "YEARS, 4, 4 anos"
    ])
    fun testGl(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val before = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, before).humanize(), equalTo(result))
    }

    @ParameterizedTest(name = "{index} => plus {1} {0} withSuffix = {2}")
    @CsvSource(value = [
        "SECONDS, 3, nuns segundos",
        "SECONDS, 45, en 45 segundos",
        "SECONDS, 48, nun minuto",
        "MINUTES, 3, en 3 minutos",
        "MINUTES, 45, nunha hora",
        "HOURS, 3, en 3 horas",
        "HOURS, 22, nun día",
        "DAYS, 3, en 3 días",
        "DAYS, 26, nun mes",
        "DAYS, 93, en 3 meses",
        "DAYS, 341, nun ano",
        "YEARS, 4, en 4 anos"
    ])
    fun testFuture(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val future = dateTime.plus(value, unit)

        assertThat(Duration.between(dateTime, future).humanize(true), equalTo(result))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("hai 5 minutos"))
    }
}