package me.nadetdev.playwright.tutos;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import me.nadetdev.playwright.config.PlaywrightChromeOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

@UsePlaywright(PlaywrightChromeOptions.class)
public class WaitingElementAppearDisappearTest {

  @BeforeEach
  void setUp(Page page) {
    page.navigate("https://practicesoftwaretesting.com");
    page.waitForCondition(() -> page.getByTestId("product-price").count() > 0);
  }

  @DisplayName("waiting element appears")
  @Test
  void shouldWaitElementAppear(Page page) {
    page.getByText("Bolt Cutters").click();
    page.getByText("Add to cart").click();

    PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
    PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT))
        .hasText("Product added to shopping cart.");

    page.waitForCondition(() -> page.getByRole(AriaRole.ALERT).isHidden());
    page.getByText("Add to cart").click();

    PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
    PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT))
        .hasText("Product added to shopping cart.");

    page.waitForCondition(() -> page.getByRole(AriaRole.ALERT).isHidden());
    // page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals("2"));
    page.waitForSelector("[data-test=cart-quantity]:has-text('2')");

    PlaywrightAssertions.assertThat(page.getByTestId("cart-quantity")).hasText("2");
  }

  @DisplayName("waiting for API calls")
  @Test
  void shouldWaitingForApiCall(Page page) {
    // page.getByTestId("product-price").first().waitFor();
    page.waitForResponse(
        "**/products?sort**", () -> page.getByTestId("sort").selectOption("Price (High - Low)"));

    var productPrices =
        page.getByTestId("product-price").allInnerTexts().stream()
            .map(price -> Double.parseDouble(price.replace("$", "")))
            .toList();

    Assertions.assertThat(productPrices)
        .isNotEmpty()
        .isSortedAccordingTo(Comparator.reverseOrder());
  }
}
