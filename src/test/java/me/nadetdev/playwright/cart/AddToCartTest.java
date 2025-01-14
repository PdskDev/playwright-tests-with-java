package me.nadetdev.playwright.cart;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import java.nio.file.Paths;
import java.util.List;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import me.nadetdev.playwright.cart.objects.CartLineItem;
import me.nadetdev.playwright.cart.objects.CheckoutPage;
import me.nadetdev.playwright.config.PlaywrightChromeOptions;
import me.nadetdev.playwright.fixtures.ScreenManager;
import me.nadetdev.playwright.home.HomePage;
import me.nadetdev.playwright.navbar.objects.NavBarComponent;
import me.nadetdev.playwright.products.ProductDetailsPage;
import me.nadetdev.playwright.products.ProductListPage;
import me.nadetdev.playwright.search.objects.SearchComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

@DisplayName("Adding items to the cart")
@Feature("Shopping Cart")
@UsePlaywright(PlaywrightChromeOptions.class)
public class AddToCartTest {
  HomePage homePage;
  SearchComponent searchComponent;
  ProductListPage productList;
  ProductDetailsPage productDetailsPage;
  NavBarComponent navBar;
  CheckoutPage checkoutPage ;

  @BeforeEach
  void openPage(Page page) {
    page.navigate("https://practicesoftwaretesting.com");

    homePage = new HomePage(page);
    searchComponent = new SearchComponent(page);
    productList = new ProductListPage(page);
    productDetailsPage = new ProductDetailsPage(page);
    navBar = new NavBarComponent(page);
    checkoutPage = new CheckoutPage(page);
  }

  @BeforeEach
  void setupTrace(BrowserContext context){
    context.tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));
  }

  @AfterEach
  void recordTrace(TestInfo testInfo, BrowserContext context){
    String traceName = testInfo.getDisplayName().replace(" ", "-").toLowerCase();
    context.tracing().stop(new Tracing.StopOptions()
            .setPath(Paths.get("target/traces/trace-" + traceName + ".zip")));
  }

  @AfterEach
  void takeAScreenShot(Page page){
    ScreenManager.takeScreenshot(page, "Final screen");
  }

  @Test
  @Story("Search and add to cart without page objects")
  @DisplayName("Without Page Objects")
  void withoutPageObjects(Page page) {
    // Search for pliers
    page.waitForResponse(
        "**/products/search?q=pliers",
        () -> {
          page.getByPlaceholder("Search").fill("pliers");
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
    // Show details page
    page.locator(".card").getByText("Combination Pliers").click();

    // Increase cart quantity
    page.getByTestId("increase-quantity").click();
    page.getByTestId("increase-quantity").click();
    // Add to cart
    page.getByText("Add to cart").click();
    page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals("4"));

    // Open the cart
    page.getByTestId("nav-cart").click();

    // check cart contents
    assertThat(page.locator(".product-title").getByText("Combination Pliers")).isVisible();
    assertThat(page.getByTestId("cart-quantity").getByText("3")).isVisible();
  }

  @Test
  @Story("Search and add to cart with page objects")
  @DisplayName("With Page Objects")
  void withObjectsAddToCartAndViewCart() {
    searchComponent.searchBy("pliers");
    productList.viewProductDetails("Combination Pliers");

    productDetailsPage.increaseQuantityBy(2);
    productDetailsPage.addToCart();

    navBar.openCart();

    List<CartLineItem> lineItems = checkoutPage.getLineItems();

    Assertions.assertThat(lineItems)
        .hasSize(1)
        .first()
        .satisfies(
            item -> {
              Assertions.assertThat(item.title()).contains("Combination Pliers");
              Assertions.assertThat(item.quantity()).isEqualTo(3);
              Assertions.assertThat(item.total()).isEqualTo(item.quantity() * item.price());
            });
  }

  @Test
  @Story("Search and add multiple items to cart with page objects")
  @DisplayName("WithPage Objects add multiple items to cart")
  void withObjectAddMultipleItemsToCard() {
    //Add first item
    searchComponent.searchBy("pliers");
    productList.viewProductDetails("Combination Pliers");
    productDetailsPage.increaseQuantityBy(2);
    productDetailsPage.addToCart();

    //Go to Home page
    homePage.open();

    //Add second item
    searchComponent.searchBy("claw");
    productList.viewProductDetails("Claw hammer with Shock Reduction Grip");
    productDetailsPage.increaseQuantityBy(1);
    productDetailsPage.addToCart();

    //Go to Home page
    homePage.open();

    //Add second item
    searchComponent.searchBy("drill");
    productList.viewProductDetails("Cordless Drill 24V");
    productDetailsPage.increaseQuantityBy(1);
    productDetailsPage.addToCart();

    navBar.openCart();

    List<CartLineItem> lineItems = checkoutPage.getLineItems();

    Assertions.assertThat(lineItems)
            .hasSize(3)
            .allSatisfy(
                    item -> {
                      Assertions.assertThat(item.price()).isGreaterThanOrEqualTo(1);
                      Assertions.assertThat(item.quantity()).isGreaterThanOrEqualTo(1);
                      Assertions.assertThat(item.total()).isGreaterThan(0.0);
                      Assertions.assertThat(item.total()).isEqualTo(item.quantity() * item.price());
                    });

    List<String> productNames = lineItems.stream().map(CartLineItem::title).toList();
    Assertions.assertThat(productNames).contains("Combination Pliers", "Claw Hammer with Shock Reduction Grip", "Cordless Drill 24V");
  }

  @Test
  @Story("Search for unknown product name")
  @DisplayName("search for unknown product name")
  void whenSearchUnknownProductName() {

    searchComponent.searchBy("no-product-name");
    var machingProducts = productList.getProductNames();

    Assertions.assertThat(machingProducts).isEmpty();
    Assertions.assertThat(productList.noProductsFound()).contains("There are no products found.");
  }


  @Test
  @Story("Reset search form and get default products")
  @DisplayName("reset search form and get default products")
  void whenResetSearchFormAndGetDefaultProducts() {

    searchComponent.searchBy("pliers");
    var machingProductsOfFirstSearch = productList.getProductNames();

    Assertions.assertThat(machingProductsOfFirstSearch).hasSize(4);

    searchComponent.clearSearch();
    var machingProductsAfterReset = productList.getProductNames();

    Assertions.assertThat(machingProductsAfterReset).hasSize(9);
  }

}
