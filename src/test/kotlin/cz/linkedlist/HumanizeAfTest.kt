package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Stream

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
class HumanizeAfTest {

    class AfArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(p0: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                    Arguments.of("SECONDS", 3L, "'n paar sekondes"),
                    Arguments.of("SECONDS", 45L, "45 sekondes"),
                    Arguments.of("SECONDS", 48L, "'n minuut"),
                    Arguments.of("MINUTES", 1L, "'n minuut"),
                    Arguments.of("MINUTES", 3L, "3 minute"),
                    Arguments.of("MINUTES", 45L, "'n uur"),
                    Arguments.of("HOURS", 1L, "'n uur"),
                    Arguments.of("HOURS", 3L, "3 ure"),
                    Arguments.of("HOURS", 22L, "'n dag"),
                    Arguments.of("DAYS", 1L, "'n dag"),
                    Arguments.of("DAYS", 3L, "3 dae"),
                    Arguments.of("DAYS", 26L, "'n maand"),
                    Arguments.of("MONTHS", 1L, "'n maand"),
                    Arguments.of("DAYS", 93L, "3 maande"),
                    Arguments.of("DAYS", 341L, "'n jaar"),
                    Arguments.of("YEARS", 1L, "'n jaar"),
                    Arguments.of("YEARS", 4L, "4 jaar")
            )
        }
    }

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("af"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
//    ])
    @ArgumentsSource(AfArgumentsProvider::class)
    fun testAf(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("oor 5 minute"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minute gelede"))
    }
}
