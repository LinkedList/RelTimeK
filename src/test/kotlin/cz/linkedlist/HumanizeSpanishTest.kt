package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class HumanizeSpanishTest {

  val SPANISH: Locale = Locale.forLanguageTag("es")

  @Test
  fun testMinuteSpanishLocale() {
    val now = LocalDateTime.now()
    val minuteBefore = now.minusMinutes(1)

    val duration = Duration.between(now, minuteBefore)
    assertThat(duration.humanize(SPANISH), equalTo("un minuto"))
  }
}

