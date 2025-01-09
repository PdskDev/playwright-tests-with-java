package me.nadetdev.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class LocatorsTest {
  protected static Playwright playwright;
  protected static Browser browser;
  protected static BrowserContext browserContext;

  Page page;

  @BeforeAll
  static void setUpBrowser() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
                    .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
    );
  }

  @BeforeEach
  void setUp() {
    browserContext = browser.newContext();
    page = browserContext.newPage();
  }

  @AfterEach
  void closeContext() {
    browserContext.close();
  }

  @AfterAll
  static void tearDown() {
    browser.close();
    playwright.close();
  }

  @DisplayName("Locating element by text")
  @Nested
  class LocaltingElementsByText {

    @BeforeEach
    void openTheCatalogPage() {
      openPage();
    }

    @DisplayName("Locate element by text content")
    @Test
    void byText() {
      page.getByText("Combination Pliers").click();
      PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
    }

    @DisplayName("Locate element by alt text")
    @Test
    void byAltText() {
      page.getByAltText("Bolt Cutters").click();
      PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @DisplayName("Locate element by title")
    @Test
    void byTitle() {
      page.getByAltText("Bolt Cutters").click();
      page.getByTitle("Practice Software Testing - Toolshop").click();
      PlaywrightAssertions.assertThat(page.getByText("Combination Pliers")).isVisible();
    }
  }


  private void openPage() {
    page.navigate("https://practicesoftwaretesting.com");
    page.waitForLoadState(LoadState.NETWORKIDLE);
  }
}
