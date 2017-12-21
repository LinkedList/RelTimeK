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
class HumanizeMyTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("my"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, စက္ကန်.အနည်းငယ်",
        "SECONDS, 45, 45 စက္ကန့်",
        "SECONDS, 48, တစ်မိနစ်",
        "MINUTES, 1, တစ်မိနစ်",
        "MINUTES, 3, 3 မိနစ်",
        "MINUTES, 45, တစ်နာရီ",
        "HOURS, 1, တစ်နာရီ",
        "HOURS, 3, 3 နာရီ",
        "HOURS, 22, တစ်ရက်",
        "DAYS, 1, တစ်ရက်",
        "DAYS, 3, 3 ရက်",
        "DAYS, 26, တစ်လ",
        "MONTHS, 1, တစ်လ",
        "DAYS, 93, 3 လ",
        "DAYS, 341, တစ်နှစ်",
        "YEARS, 1, တစ်နှစ်",
        "YEARS, 4, 4 နှစ်"
    ])
    fun testMy(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("လာမည့် 5 မိနစ် မှာ"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("လွန်ခဲ့သော 5 မိနစ် က"))
    }
}
