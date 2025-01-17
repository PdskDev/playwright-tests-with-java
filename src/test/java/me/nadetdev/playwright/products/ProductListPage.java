package me.nadetdev.playwright.products;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import java.util.List;
import me.nadetdev.playwright.fixtures.ProductSummary;
import me.nadetdev.playwright.fixtures.ScreenManager;

public class ProductListPage {
  private final Page page;

  public ProductListPage(Page page) {
    this.page = page;
  }

  public List<ProductSummary> getProductsSummaries() {
    return page.locator(".card").all().stream()
        .map(
            productCard -> {
              String productName = productCard.getByTestId("product-name").innerText().strip();
              String productPrice = productCard.getByTestId("product-price").innerText().strip();
              return new ProductSummary(productName, productPrice);
            })
        .toList();
  }

  @Step("Get product names")
  public List<String> getProductNames() {
    ScreenManager.takeScreenshot(page, "Get product names");
    return page.getByTestId("product-name").allInnerTexts();
  }

  @Step("View product details")
  public void viewProductDetails(String productName) {
    page.locator(".card").getByText(productName).click();
    ScreenManager.takeScreenshot(page, "View product details");
  }

  @Step("No products found")
  public String noProductsFound() {
    page.getByTestId("search_completed").waitFor();
    ScreenManager.takeScreenshot(page, "No products found");
    return page.getByTestId("search_completed").innerText();
  }
}
