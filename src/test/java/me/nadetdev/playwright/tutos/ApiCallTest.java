package me.nadetdev.playwright.tutos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import java.util.HashMap;
import java.util.stream.Stream;
import me.nadetdev.playwright.config.PlaywrightChromeOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@UsePlaywright(PlaywrightChromeOptions.class)
public class ApiCallTest {

  private static APIRequestContext apiRequestContext;

  @BeforeAll
  public static void setupRequestContext(Playwright playwright) {
    apiRequestContext =
        playwright
            .request()
            .newContext(
                new APIRequest.NewContextOptions()
                    .setBaseURL("https://api.practicesoftwaretesting.com")
                    .setExtraHTTPHeaders(
                        new HashMap<>() {
                          {
                            put("Accept", "application/json");
                          }
                        }));
  }

  static Stream<Product> products() {
    APIResponse response = apiRequestContext.get("/products?page=2");
    Assertions.assertThat(response.status()).isEqualTo(200);

    JsonObject responseObject = new Gson().fromJson(response.text(), JsonObject.class);
    JsonArray data = responseObject.getAsJsonArray("data");

    return data.asList().stream()
        .map(
            elementProduct -> {
              JsonObject jsonProduct = elementProduct.getAsJsonObject();
              return new Product(
                  jsonProduct.get("name").getAsString(), jsonProduct.get("price").getAsDouble());
            });
  }

  @BeforeEach
  void setUp(Page page) {
    page.navigate("https://practicesoftwaretesting.com");
    page.waitForCondition(() -> page.getByTestId("product-price").count() > 0);
  }

  @DisplayName("Check presence of known products")
  @ParameterizedTest(name = "Checking product {0}")
  @MethodSource("products")
  void shouldCheckKnownProduct(Product product, Page page) {
    page.fill("[placeholder='Search']", product.name);
    page.click("button:has-text('Search')");

    // Check correct name
    Locator productCard =
        page.locator(".card")
            .filter(
                new Locator.FilterOptions()
                    .setHasText(product.name)
                    .setHasText(Double.toString(product.price)));

    PlaywrightAssertions.assertThat(productCard).isVisible();
  }

  record Product(String name, Double price) {}
}
