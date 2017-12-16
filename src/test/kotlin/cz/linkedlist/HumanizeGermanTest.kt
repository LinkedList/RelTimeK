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

class HumanizeGermanTest {
    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.GERMAN)
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ein paar Sekunden",
        "SECONDS, 45, 45 Sekunden",
        "SECONDS, 48, eine Minute",
        "MINUTES, 1, eine Minute",
        "MINUTES, 3, 3 Minuten",
        "MINUTES, 45, eine Stunde",
        "HOURS, 1, eine Stunde",
        "HOURS, 3, 3 Stunden",
        "HOURS, 22, ein Tag",
        "DAYS, 1, ein Tag",
        "DAYS, 3, 3 Tage",
        "DAYS, 26, ein Monat",
        "MONTHS, 1, ein Monat",
        "DAYS, 93, 3 Monate",
        "DAYS, 341, ein Jahr",
        "YEARS, 1, ein Jahr",
        "YEARS, 4, 4 Jahre"
    ])
    fun testGerman(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val before = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, before).humanize(), equalTo(result))
    }

    @ParameterizedTest(name = "{index} => plus {1} {0} withSuffix = {2}")
    @CsvSource(value = [
        "SECONDS, 3, in ein paar Sekunden",
        "SECONDS, 45, in 45 Sekunden",
        "MINUTES, 1, in einer Minute",
        "MINUTES, 3, in 3 Minuten",
        "HOURS, 1, in einer Stunde",
        "HOURS, 3, in 3 Stunden",
        "DAYS, 1, in einem Tag",
        "DAYS, 3, in 3 Tagen",
        "MONTHS, 1, in einem Monat",
        "DAYS, 93, in 3 Monaten",
        "YEARS, 1, in einem Jahr",
        "YEARS, 4, in 4 Jahren"
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
        "SECONDS, 3, vor ein paar Sekunden",
        "SECONDS, 45, vor 45 Sekunden",
        "MINUTES, 1, vor einer Minute",
        "MINUTES, 3, vor 3 Minuten",
        "HOURS, 1, vor einer Stunde",
        "HOURS, 3, vor 3 Stunden",
        "DAYS, 1, vor einem Tag",
        "DAYS, 3, vor 3 Tagen",
        "MONTHS, 1, vor einem Monat",
        "DAYS, 93, vor 3 Monaten",
        "YEARS, 1, vor einem Jahr",
        "YEARS, 4, vor 4 Jahren"
    ])
    fun testPast(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val future = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, future).humanize(true), equalTo(result))
    }
}