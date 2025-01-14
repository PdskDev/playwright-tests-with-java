package me.nadetdev.playwright.products;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import me.nadetdev.playwright.fixtures.ScreenManager;

public class ProductDetailsPage {
  private final Page page;

  public ProductDetailsPage(Page page) {
    this.page = page;
  }

  @Step("Increase quantity by {increment}")
  public void increaseQuantityBy(int increment) {
    for (int i = 0; i < increment; i++) {
      page.getByTestId("increase-quantity").click();
    }
    ScreenManager.takeScreenshot(page, "Increase quantity");
  }


  @Step("Add to cart")
  public void addToCart() {
    //page.getByText("Add to cart").click();
    page.waitForResponse(
            response -> response.url().contains("/carts") && response.request().method().equals("POST"),
            () -> {
              page.getByText("Add to cart").click();
              page.getByRole(AriaRole.ALERT).click();
            });
    ScreenManager.takeScreenshot(page, "Add to cart");
  }
}
