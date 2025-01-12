package me.nadetdev.playwright.cart;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import me.nadetdev.playwright.cart.objects.CartLineItem;
import me.nadetdev.playwright.cart.objects.CheckoutPage;
import me.nadetdev.playwright.cart.objects.ProductDetailsPage;
import me.nadetdev.playwright.fixtures.PlaywrightTestingBase;
import me.nadetdev.playwright.home.HomePage;
import me.nadetdev.playwright.navbar.objects.NavBar;
import me.nadetdev.playwright.search.objects.ProductList;
import me.nadetdev.playwright.search.objects.SearchComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddToCartTest extends PlaywrightTestingBase {
  HomePage homePage;
  SearchComponent searchComponent;
  ProductList productList;
  ProductDetailsPage productDetailsPage;
  NavBar navBar;
  CheckoutPage checkoutPage ;

  @BeforeEach
  void openPage() {
    page.navigate("https://practicesoftwaretesting.com");

    homePage = new HomePage(page);
    searchComponent = new SearchComponent(page);
    productList = new ProductList(page);
    productDetailsPage = new ProductDetailsPage(page);
    navBar = new NavBar(page);
    checkoutPage = new CheckoutPage(page);
  }

  @DisplayName("Without Page Objects")
  @Test
  void withoutPageObjects() {
    // Search for pliers
    page.waitForResponse(
        "**/products/search?q=pliers",
        () -> {
          page.getByPlaceholder("Search").fill("pliers");
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
    // Show details page
    page.locator(".card").getByText("Combination Pliers").click();

    // Increase cart quanity
    page.getByTestId("increase-quantity").click();
    page.getByTestId("increase-quantity").click();
    // Add to cart
    page.getByText("Add to cart").click();
    page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals("3"));

    // Open the cart
    page.getByTestId("nav-cart").click();

    // check cart contents
    assertThat(page.locator(".product-title").getByText("Combination Pliers")).isVisible();
    assertThat(page.getByTestId("cart-quantity").getByText("3")).isVisible();
  }

  @DisplayName("Without Page Objects")
  @Test
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

  @DisplayName("WithPage Objects add multiple items to cart")
  @Test
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
            .first()
            .satisfies(
                    item -> {
                      Assertions.assertThat(item.title()).contains("Combination Pliers");
                      Assertions.assertThat(item.quantity()).isEqualTo(3);
                      Assertions.assertThat(item.total()).isEqualTo(item.quantity() * item.price());
                    });

    List<String> productNames = lineItems.stream().map(CartLineItem::title).toList();
    Assertions.assertThat(productNames).contains("Combination Pliers", "Claw Hammer with Shock Reduction Grip", "Cordless Drill 24V");
  }

}
