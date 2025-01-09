package me.nadetdev.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

@UsePlaywright
public class SimpleTest {

  @Test
  void shouldShowThePageTitle(Page page) {
    page.navigate("https://practicesoftwaretesting.com/");
    String pageTitle = page.title();

    Assertions.assertTrue(pageTitle.contains("Practice Software Testing"));
  }

  @Test
  void shouldSearchByKeyword(Page page){
    page.navigate("https://practicesoftwaretesting.com/");
    page.locator("[placeholder=Search]").fill("Pliers");
    page.locator("button:has-text('Search')").click();

    int matchingSearchResults = page.locator(".card").count();

    Assertions.assertTrue(matchingSearchResults > 0);
  }
}
