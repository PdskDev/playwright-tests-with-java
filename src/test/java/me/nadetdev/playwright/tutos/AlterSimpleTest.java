package me.nadetdev.playwright.tutos;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@UsePlaywright(AlterSimpleTest.CustomOptions.class)
public class AlterSimpleTest {

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
          .setHeadless(true)
          .setLaunchOptions(
              new BrowserType.LaunchOptions()
                  .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu")));
    }
  }
}
