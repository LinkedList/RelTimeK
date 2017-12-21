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
class HumanizeBoTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("bo"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ལམ་སང",
        "SECONDS, 45, 45 སྐར་ཆ།",
        "SECONDS, 48, སྐར་མ་གཅིག",
        "MINUTES, 1, སྐར་མ་གཅིག",
        "MINUTES, 3, 3 སྐར་མ",
        "MINUTES, 45, ཆུ་ཚོད་གཅིག",
        "HOURS, 1, ཆུ་ཚོད་གཅིག",
        "HOURS, 3, 3 ཆུ་ཚོད",
        "HOURS, 22, ཉིན་གཅིག",
        "DAYS, 1, ཉིན་གཅིག",
        "DAYS, 3, 3 ཉིན་",
        "DAYS, 26, ཟླ་བ་གཅིག",
        "MONTHS, 1, ཟླ་བ་གཅིག",
        "DAYS, 93, 3 ཟླ་བ",
        "DAYS, 341, ལོ་གཅིག",
        "YEARS, 1, ལོ་གཅིག",
        "YEARS, 4, 4 ལོ"
    ])
    fun testBo(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 སྐར་མ ལ་"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 སྐར་མ སྔན་ལ"))
    }
}
