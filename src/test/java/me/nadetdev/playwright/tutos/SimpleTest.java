package me.nadetdev.playwright.tutos;

import com.microsoft.playwright.*;

import java.util.Arrays;

import org.junit.jupiter.api.*;

public class SimpleTest {

  private static Playwright playwright;
  private static Browser browser;
  private static BrowserContext browserContext;
  private Page page;

  @BeforeAll
  public static void setupBrowser() {
    playwright = Playwright.create();
    browser =
            playwright
                    .chromium()
                    .launch(
                            new BrowserType.LaunchOptions()
                                    .setHeadless(true)
                                    .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu")));

    browserContext = browser.newContext();
  }

  @BeforeEach
  public void setupPage() {
    page = browserContext.newPage();
  }

  @AfterAll
  public static void teardown() {
    browser.close();
    playwright.close();
  }

  @Test
  void shouldShowThePageTitle() {
    page.navigate("https://practicesoftwaretesting.com/");
    String pageTitle = page.title();

    Assertions.assertTrue(pageTitle.contains("Practice Software Testing"));
  }

  @Test
  void shouldSearchByKeyword() {
    page.navigate("https://practicesoftwaretesting.com/");
    page.locator("[placeholder=Search]").fill("Pliers");
    page.locator("button:has-text('Search')").click();

    int matchingSearchResults = page.locator(".card").count();

    Assertions.assertTrue(matchingSearchResults > 0);
  }
}
