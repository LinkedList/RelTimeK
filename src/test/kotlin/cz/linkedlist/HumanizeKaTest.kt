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

class HumanizeKaTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ka"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, რამდენიმე წამი",
        "SECONDS, 45, 45 წამი",
        "SECONDS, 48, წუთი",
        "MINUTES, 1, წუთი",
        "MINUTES, 3, 3 წუთი",
        "MINUTES, 45, საათი",
        "HOURS, 1, საათი",
        "HOURS, 3, 3 საათი",
        "HOURS, 22, დღე",
        "DAYS, 1, დღე",
        "DAYS, 3, 3 დღე",
        "DAYS, 26, თვე",
        "MONTHS, 1, თვე",
        "DAYS, 93, 3 თვე",
        "DAYS, 341, წელი",
        "YEARS, 1, წელი",
        "YEARS, 4, 4 წელი"
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
        "SECONDS, 3, რამდენიმე წამში",
        "SECONDS, 45, 45 წამში",
        "SECONDS, 48, წუთში",
        "MINUTES, 1, წუთში",
        "MINUTES, 3, 3 წუთში",
        "MINUTES, 45, საათში",
        "HOURS, 1, საათში",
        "HOURS, 3, 3 საათში",
        "HOURS, 22, დღეში",
        "DAYS, 1, დღეში",
        "DAYS, 3, 3 დღეში",
        "DAYS, 26, თვეში",
        "MONTHS, 1, თვეში",
        "DAYS, 93, 3 თვეში",
        "DAYS, 341, წელში",
        "YEARS, 1, წელში",
        "YEARS, 4, 4 წელში"
    ])
    fun testFuture(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val future = dateTime.plus(value, unit)

        assertThat(Duration.between(dateTime, future).humanize(true), equalTo(result))
    }

    @ParameterizedTest(name = "{index} => plus {1} {0} withSuffix = {2}")
    @CsvSource(value = [
        "SECONDS, 3, რამდენიმე წამის უკან",
        "SECONDS, 45, 45 წამის უკან",
        "SECONDS, 48, წუთის უკან",
        "MINUTES, 1, წუთის უკან",
        "MINUTES, 3, 3 წუთის უკან",
        "MINUTES, 45, საათის უკან",
        "HOURS, 1, საათის უკან",
        "HOURS, 3, 3 საათის უკან",
        "HOURS, 22, დღის უკან",
        "DAYS, 1, დღის უკან",
        "DAYS, 3, 3 დღის უკან",
        "DAYS, 26, თვის უკან",
        "MONTHS, 1, თვის უკან",
        "DAYS, 93, 3 თვის უკან",
        "DAYS, 341, წლის უკან",
        "YEARS, 1, წლის უკან",
        "YEARS, 4, 4 წლის უკან"
    ])
    fun testPast(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val future = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, future).humanize(true), equalTo(result))
    }
}