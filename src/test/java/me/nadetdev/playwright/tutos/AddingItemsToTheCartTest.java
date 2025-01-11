package me.nadetdev.playwright.tutos;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import me.nadetdev.playwright.config.HeadlessChromeOptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@UsePlaywright(HeadlessChromeOptions.class)
public class AddingItemsToTheCartTest {

    @DisplayName("Search for pliers")
    @Test
    void searchForPliers(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByPlaceholder("Search").fill("pliers");
        page.getByPlaceholder("Search").press("Enter");

        PlaywrightAssertions.assertThat(page.locator(".card")).hasCount(4);

        List<String> productNames = page.getByTestId("product-name").allTextContents();

        org.assertj.core.api.Assertions.assertThat(productNames).allMatch(name -> name.contains("Pliers"));

        Locator outOfStockItem = page.locator(".card")
                .filter(new Locator.FilterOptions().setHasText("Out of stock"))
                .getByTestId("product-name");

        PlaywrightAssertions.assertThat(outOfStockItem).hasCount(1);
        PlaywrightAssertions.assertThat(outOfStockItem).hasText("Long Nose Pliers");
    }


}
