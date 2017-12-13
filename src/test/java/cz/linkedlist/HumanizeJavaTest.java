package cz.linkedlist;

import org.junit.jupiter.api.Test;

import cz.linkedlist.reltimek.Humanize;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class HumanizeJavaTest {

  @Test
  void testMinute() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime before = now.minusMinutes(1);
    Locale.setDefault(Locale.US);

    Duration duration = Duration.between(now, before);
    assertThat(Humanize.humanize(duration), equalTo("a minute"));
  }
}
