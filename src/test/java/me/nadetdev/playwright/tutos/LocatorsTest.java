package me.nadetdev.playwright.tutos;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import java.util.List;

import me.nadetdev.playwright.config.PlaywrightChromeOptions;
import org.junit.jupiter.api.*;

@UsePlaywright(PlaywrightChromeOptions.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class LocatorsTest {


  private void openPage(Page page) {
    page.navigate("https://practicesoftwaretesting.com");
    page.waitForLoadState(LoadState.NETWORKIDLE);
  }

  @DisplayName("Locating element by text")
  @Nested
  @TestMethodOrder(MethodOrderer.MethodName.class)
  class LocaltingElementsByText {

    @BeforeEach
    void openTheCatalogPage(Page page) {
      openPage(page);
    }

    @DisplayName("Locate element by text content")
    @Test
    void byText(Page page) {
      page.getByText("Combination Pliers").click();
      PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
    }

    @DisplayName("Locate element by alt text")
    @Test
    void byAltText(Page page) {
      page.getByAltText("Bolt Cutters").click();
      PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @DisplayName("Locate element by title")
    @Test
    void byTitle(Page page) {
      page.getByAltText("Bolt Cutters").click();
      page.getByTitle("Practice Software Testing - Toolshop").click();
      PlaywrightAssertions.assertThat(page.getByText("Combination Pliers")).isVisible();
    }
  }

  @DisplayName("Locating element by labels and placeholder")
  @Nested
  @TestMethodOrder(MethodOrderer.MethodName.class)
  class LocaltingElementsByLabelsAndPlaceholders {

    @BeforeEach
    void openTheCatalogPage(Page page) {
      openPage(page);
    }

    @DisplayName("Locate element by label")
    @Test
    void byLabelAndPlaceholder(Page page) {
      page.getByText("Contact").click();
      page.getByLabel("First name").fill("Play");
      page.getByPlaceholder("Your last name").fill("Wright");
      page.getByPlaceholder("email").fill("test@email.com");
    }
  }

  @DisplayName("Locating element by TestId")
  @Nested
  @TestMethodOrder(MethodOrderer.MethodName.class)
  class LocaltingElementsByTestId {

    @BeforeEach
    void openTheCatalogPage(Page page) {
      openPage(page);
    }

    @DisplayName("Locate element by test id")
    @Test
    void byTestId(Page page) {
      page.getByTestId("search-query").fill("pliers");
      page.getByTestId("search-submit").click();
      PlaywrightAssertions.assertThat(page.getByTestId("search-caption")).isVisible();
      PlaywrightAssertions.assertThat(page.getByTestId("search-caption"))
          .containsText("Searched for: pliers");
    }
  }

  @DisplayName("Locating element by multiple match")
  @Nested
  @TestMethodOrder(MethodOrderer.MethodName.class)
  class LocaltingElementsWithMultiplematch {

    @BeforeEach
    void openTheCatalogPage(Page page) {
      openPage(page);
    }

    @DisplayName("Locate element by first match")
    @Test
    void byFirstElement(Page page) {
      page.locator(".card").first().click();
      PlaywrightAssertions.assertThat(page.getByText("Combination Pliers")).isVisible();
    }

    @DisplayName("Locate element by last match")
    @Test
    void byLastElement(Page page) {
      page.locator(".card").last().click();
      PlaywrightAssertions.assertThat(page.getByText("Thor Hammer")).isVisible();
    }

    @DisplayName("Locate element by nth match")
    @Test
    void byNthElement(Page page) {
      page.locator(".card").nth(4).click();
      PlaywrightAssertions.assertThat(page.getByText("Slip Joint Pliers")).isVisible();
    }

    @DisplayName("Locate element by all matchs")
    @Test
    void byAllElements(Playwright playwright, Page page) {
      playwright.selectors().setTestIdAttribute("data-test");
      //List<String> itemNames = page.getByTestId("product-name").allTextContents();
      page.getByTestId("product-name").nth(5).click();
      PlaywrightAssertions.assertThat(page.getByText("Claw Hammer with Shock Reduction Grip")).isVisible();
    }
  }

  @DisplayName("Locating element by CSS Selectors")
  @Nested
  @TestMethodOrder(MethodOrderer.MethodName.class)
  class LocaltingElementsByCSSSelectors {

    @BeforeEach
    void openTheCatalogPage(Page page) {
      openPage(page);
    }

    @DisplayName("Locate element by tag + attribute")
    @Test
    void byCssTagAndAttribute(Page page) {
      page.locator("img[alt='Combination Pliers']").click();
      PlaywrightAssertions.assertThat(page.getByText("Combination Pliers")).isVisible();
    }

    @DisplayName("Locate element by CSS selector")
    @Test
    void byCssClassSelector(Page page) {
      PlaywrightAssertions.assertThat(page.locator(".img-fluid")).isVisible();
    }

    @DisplayName("Locate element by CSS selector")
    @Test
    void byCssHasText(Page page) {
      page.locator("a:has-text('Sign in')").click();
      PlaywrightAssertions.assertThat(page.locator("h3:has-text('Login')")).isVisible();
    }

    @DisplayName("Locate element by CSS Id")
    @Test
    void byCssId(Page page) {
      page.locator("#language").click();
      page.locator("div[class='btn-group dropdown'] li:nth-child(4) a:nth-child(1)").click();
      PlaywrightAssertions.assertThat(page.locator(".grid-title").nth(1)).containsText("Fourchette de prix");
    }


  }

  @DisplayName("Locating element by CSS Selectors")
  @Nested
  @TestMethodOrder(MethodOrderer.MethodName.class)
  class LocaltingElementsUsingCSS {

    @BeforeEach
    void openTheCatalogPage(Page page) {
      page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("Locate input text by Id")
    @Test
    void locateInputFirstNameById(Page page) {
      page.locator("#first_name").fill("NadetDev");
      PlaywrightAssertions.assertThat(page.locator("#first_name")).hasValue("NadetDev");
    }

    @DisplayName("Locate button by Css class")
    @Test
    void locateButtonByCssClass(Page page) {
      page.locator("#first_name").fill("NadetDev");
      page.locator(".btnSubmit").click();
      page.waitForSelector(".alert");

      List<String> alertMessages = page.locator(".alert").allTextContents();

      Assertions.assertFalse(alertMessages.isEmpty());
    }

    /*@DisplayName("Locate input text by attribute")
    @Test
    void locateInputByAttribute(Page page) {
      page.locator("[placeholder='Your last name *']").fill("Fashion Dev");

      PlaywrightAssertions.assertThat(page.locator("[placeholder='Your last name *']")).hasValue("Fashion Dev");

      page.locator(".btnSubmit").click();
      List<String> alertMessages = page.locator(".alert").allTextContents();

      Assertions.assertFalse(alertMessages.isEmpty());
      Assertions.assertEquals(4, alertMessages.size());
    }*/

    @DisplayName("Locate list and filter element")
    @Test
    void locateListAndFilterElement(Page page) {
      page.getByText("Home").click();
      page.getByLabel("Hammer").click();

      PlaywrightAssertions.assertThat(page.getByLabel("Hammer")).isChecked();

      page.waitForSelector("h5:has-text('Sledgehammer')");

      PlaywrightAssertions.assertThat(page.locator("h5:has-text('Sledgehammer')")).isVisible();

      List<String> productNames = page.getByTestId("product-name").allTextContents();

      Assertions.assertFalse(productNames.isEmpty());
      Assertions.assertEquals(7, productNames.size());
    }

    @DisplayName("Locate list and filter element")
    @Test
    void searchOfPliers(Page page) {

      page.navigate("https://practicesoftwaretesting.com/");
      page.getByPlaceholder("Search").fill("pliers");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();

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
}
