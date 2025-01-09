package me.nadetdev.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SimpleTest {
  private Playwright playwright;
  private Browser browser;
  private Page page;

  @BeforeEach
  void setup() {
    playwright = Playwright.create();
    browser =
        playwright
            .chromium()
            .launch(
                new BrowserType.LaunchOptions()
                    .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"))
                    .setHeadless(true)
            );
    page = browser.newPage();
  }

  @AfterEach
  void teardown() {
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
