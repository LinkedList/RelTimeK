package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class HumanizeItalianTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.ITALIAN)
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, alcuni secondi",
        "SECONDS, 45, 45 secondi",
        "SECONDS, 48, un minuto",
        "MINUTES, 1, un minuto",
        "MINUTES, 3, 3 minuti",
        "MINUTES, 45, un'ora",
        "HOURS, 1, un'ora",
        "HOURS, 3, 3 ore",
        "HOURS, 22, un giorno",
        "DAYS, 1, un giorno",
        "DAYS, 3, 3 giorni",
        "DAYS, 26, un mese",
        "MONTHS, 1, un mese",
        "DAYS, 93, 3 mesi",
        "DAYS, 341, un anno",
        "YEARS, 1, un anno",
        "YEARS, 4, 4 anni"
    ])
    fun testItalian(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val before = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, before).humanize(), equalTo(result))
    }

    @ParameterizedTest(name = "{index} => plus {1} {0} withSuffix = {2}")
    @CsvSource(value = [
        "SECONDS, 3, in alcuni secondi",
        "SECONDS, 45, tra 45 secondi",
        "MINUTES, 1, in un minuto",
        "MINUTES, 3, tra 3 minuti",
        "HOURS, 1, in un'ora",
        "HOURS, 3, tra 3 ore",
        "DAYS, 1, in un giorno",
        "DAYS, 3, tra 3 giorni",
        "MONTHS, 1, in un mese",
        "DAYS, 93, tra 3 mesi",
        "YEARS, 1, in un anno",
        "YEARS, 4, tra 4 anni"
    ])
    fun testFuture(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val future = dateTime.plus(value, unit)

        assertThat(Duration.between(dateTime, future).humanize(true), equalTo(result))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} withSuffix = {2}")
    @CsvSource(value = [
        "SECONDS, 3, alcuni secondi fa",
        "SECONDS, 45, 45 secondi fa",
        "MINUTES, 1, un minuto fa",
        "MINUTES, 3, 3 minuti fa",
        "HOURS, 1, un'ora fa",
        "HOURS, 3, 3 ore fa",
        "DAYS, 1, un giorno fa",
        "DAYS, 3, 3 giorni fa",
        "MONTHS, 1, un mese fa",
        "DAYS, 93, 3 mesi fa",
        "YEARS, 1, un anno fa",
        "YEARS, 4, 4 anni fa"
    ])
    fun testPast(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val future = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, future).humanize(true), equalTo(result))
    }
}