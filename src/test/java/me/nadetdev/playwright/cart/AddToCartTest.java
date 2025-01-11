package me.nadetdev.playwright.cart;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import me.nadetdev.playwright.cart.objects.ProductDetailsPage;
import me.nadetdev.playwright.fixtures.PlaywrightTestingBase;
import me.nadetdev.playwright.search.objects.ProductList;
import me.nadetdev.playwright.search.objects.SearchComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddToCartTest extends PlaywrightTestingBase {

    @BeforeEach
    void openPage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("Without Page Objects")
    @Test
    void withoutPageObjects() {
        // Search for pliers
        page.waitForResponse("**/products/search?q=pliers", () -> {
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
    void withObjects(){
        SearchComponent searchComponent = new SearchComponent(page);
        searchComponent.searchBy("pliers");

        ProductList productList = new ProductList(page);
        productList.viewProductDetails("Combination Pliers");

        ProductDetailsPage productDetailsPage = new ProductDetailsPage(page);
        productDetailsPage.increaseQuantityBy(2);
        productDetailsPage.addToCart();
        productDetailsPage.viewCartContent(3);


    }

}
