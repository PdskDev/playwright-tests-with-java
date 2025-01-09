package me.nadetdev.playwright;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@UsePlaywright(SimpleTest.CustomOptions.class)
public class SimpleTest {

  @Test
  void shouldShowThePageTitle(Page page) {
    page.navigate("https://practicesoftwaretesting.com/");
    String pageTitle = page.title();

    Assertions.assertTrue(pageTitle.contains("Practice Software Testing"));
  }

  @Test
  void shouldSearchByKeyword(Page page) {
    page.navigate("https://practicesoftwaretesting.com/");
    page.locator("[placeholder=Search]").fill("Pliers");
    page.locator("button:has-text('Search')").click();

    int matchingSearchResults = page.locator(".card").count();

    Assertions.assertTrue(matchingSearchResults > 0);
  }

  public static class CustomOptions implements OptionsFactory {
    @Override
    public Options getOptions() {
      return new Options()
          .setHeadless(false)
          .setLaunchOptions(
              new BrowserType.LaunchOptions()
                  .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu")));
    }
  }
}
