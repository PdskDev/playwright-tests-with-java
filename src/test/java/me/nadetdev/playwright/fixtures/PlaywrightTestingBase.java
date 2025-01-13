package me.nadetdev.playwright.fixtures;

import com.microsoft.playwright.*;
import java.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class PlaywrightTestingBase {
  protected static ThreadLocal<Playwright> playwright =
      ThreadLocal.withInitial(
          () -> {
            Playwright playwright = Playwright.create();
            playwright.selectors().setTestIdAttribute("data-test");
            return playwright;
          });

  protected static ThreadLocal<Browser> browser =
      ThreadLocal.withInitial(
          () -> {
            return playwright
                .get()
                .chromium()
                .launch(
                    new BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setSlowMo(100)
                        .setArgs(
                            Arrays.asList(
                                "--no-sandbox", "--disable-extensions", "--disable-gpu")));
          });

  protected BrowserContext browserContext;

  protected Page page;

  @AfterAll
  static void tearDown() {
    browser.get().close();
    browser.remove();
    playwright.get().close();
    playwright.remove();
  }

  @BeforeEach
  void setUpBrowserContext() {
    browserContext = browser.get().newContext();
    page = browserContext.newPage();
  }

  @AfterEach
  void closeContext() {
    browserContext.close();
  }
}
