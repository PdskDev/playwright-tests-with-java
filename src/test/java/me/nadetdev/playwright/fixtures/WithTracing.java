package me.nadetdev.playwright.fixtures;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public interface WithTracing {

  @BeforeEach
  default void setupTrace(BrowserContext context) {
    context
        .tracing()
        .start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
  }

  @AfterEach
  default void recordTrace(TestInfo testInfo, BrowserContext context) {
    String traceName = testInfo.getDisplayName().replace(" ", "-").toLowerCase();
    context
        .tracing()
        .stop(
            new Tracing.StopOptions()
                .setPath(Paths.get("target/traces/trace-" + traceName + ".zip")));
  }
}
