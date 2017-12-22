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
class HumanizeCvTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("cv"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, пӗр-ик ҫеккунт",
        "SECONDS, 45, 45 ҫеккунт",
        "SECONDS, 48, пӗр минут",
        "MINUTES, 1, пӗр минут",
        "MINUTES, 3, 3 минут",
        "MINUTES, 45, пӗр сехет",
        "HOURS, 1, пӗр сехет",
        "HOURS, 3, 3 сехет",
        "HOURS, 22, пӗр кун",
        "DAYS, 1, пӗр кун",
        "DAYS, 3, 3 кун",
        "DAYS, 26, пӗр уйӑх",
        "MONTHS, 1, пӗр уйӑх",
        "DAYS, 93, 3 уйӑх",
        "DAYS, 341, пӗр ҫул",
        "YEARS, 1, пӗр ҫул",
        "YEARS, 4, 4 ҫул"
    ])
    fun testCv(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val before = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, before).humanize(), equalTo(result))
    }

    @Test
    fun testFutureNoCondition() {
        val now = LocalDateTime.now()
        val before = now.plusMinutes(5)
        assertThat(Duration.between(now, before).humanize(true), equalTo("5 минутран"))
    }

    @Test
    fun testFutureForHours() {
        val now = LocalDateTime.now()
        val before = now.plusHours(3)
        assertThat(Duration.between(now, before).humanize(true), equalTo("3 сехетрен"))
    }

    @Test
    fun testFutureForYears() {
        val now = LocalDateTime.now()
        val before = now.plusYears(3)
        assertThat(Duration.between(now, before).humanize(true), equalTo("3 ҫултан"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 минут секунд"))
    }
}
