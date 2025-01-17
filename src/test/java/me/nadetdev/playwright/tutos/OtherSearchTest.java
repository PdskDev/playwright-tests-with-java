package me.nadetdev.playwright.tutos;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import me.nadetdev.playwright.config.PlaywrightChromeOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import java.util.List;

@UsePlaywright(PlaywrightChromeOptions.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OtherSearchTest {

  @BeforeEach
  void setUp(Page page) {
    page.navigate("https://practicesoftwaretesting.com");
    page.waitForCondition(() -> page.getByTestId("product-price").count() > 0);
  }

  @DisplayName("making assertions about data values")
  @Test
  @Order(1)
  void allProductPricesShouldBeCorrectValues(Page page) {
    List<Double> prices =
        page.getByTestId("product-price").allInnerTexts().stream()
            .map(price -> Double.parseDouble(price.replace("$", "")))
            .toList();

    Assertions.assertThat(prices)
        .isNotEmpty()
        .allMatch(price -> price > 0)
        .doesNotContain(0.0)
        .allMatch(price -> price < 50)
        .allSatisfy(price -> Assertions.assertThat(price).isGreaterThan(0.0).isLessThan(50));
  }

  @DisplayName("making sort ASC")
  @Test
  @Order(2)
  void shouldSortInAlphabeticOrder(Page page) {
    page.getByTestId("sort").selectOption("Name (A - Z)");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    List<String> productNames = page.getByTestId("product-name").allTextContents();

    //Assertions.assertThat(productNames).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);
    Assertions.assertThat(productNames).isSortedAccordingTo(Comparator.naturalOrder());
    //Assertions.assertThat(productNames.getFirst().trim()).isEqualTo("Adjustable Wrench");
    //Assertions.assertThat(productNames.getLast().trim())
     //   .isEqualTo("Claw Hammer with Shock Reduction Grip");
  }

  @DisplayName("making sort DESC")
  @Test
  @Order(3)
  void shouldSortInReverseOrder(Page page) {
    page.getByTestId("sort").selectOption("Name (Z - A)");
    page.waitForLoadState(LoadState.NETWORKIDLE);

    List<String> productNames = page.getByTestId("product-name").allTextContents();

    Assertions.assertThat(productNames).isSortedAccordingTo(Comparator.reverseOrder());
    /*Assertions.assertThat(productNames.getFirst().trim()).isEqualTo("Wood Saw");
    Assertions.assertThat(productNames.getLast().trim())
            .isEqualTo("Super-thin Protection Gloves");*/
  }

  @DisplayName("waiting for element")
  @Test
  @Order(4)
  void shouldWaitShowAllProduct(Page page) {
    page.waitForSelector("[data-test='product-name']");
    List<String> productNames = page.getByTestId("product-name").allInnerTexts();

    Assertions.assertThat(productNames).contains("Combination Pliers", "Bolt Cutters", "Long Nose Pliers", "Slip Joint Pliers");

    List<String> productImageTitles = page.locator(".card-img-top").all()
            .stream()
            .map(img -> img.getAttribute("alt"))
            .toList();

    Assertions.assertThat(productImageTitles).contains("Combination Pliers", "Bolt Cutters", "Long Nose Pliers");
  }

  @DisplayName("waiting for element")
  @Test
  @Order(5)
  void shouldWaitElementCheked(Page page) {
    var screwdriverFilter = page.getByLabel("Screwdriver");
    screwdriverFilter.click();
    PlaywrightAssertions.assertThat(screwdriverFilter).isChecked();
  }

  @DisplayName("should filter products by category")
  @Test
  @Order(6)
  void shouldFilterProductByCategory(Page page) {
    page.getByRole(AriaRole.MENUBAR).getByText("Categories").click();
    page.getByRole(AriaRole.MENUBAR).getByText("Power Tools").click();

    page.waitForSelector(".card", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

    var filteredProducts = page.getByTestId("product-name").allInnerTexts();

    Assertions.assertThat(filteredProducts).contains("Circular Saw", "Cordless Drill 20V", "Belt Sander");
  }
}
