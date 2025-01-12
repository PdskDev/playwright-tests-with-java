package me.nadetdev.playwright.cart.objects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ProductDetailsPage {
  private final Page page;

  public ProductDetailsPage(Page page) {
    this.page = page;
  }

  public void increaseQuantityBy(int increment) {
    for (int i = 0; i < increment; i++) {
      page.getByTestId("increase-quantity").click();
    }
  }

  public void addToCart() {
    //page.getByText("Add to cart").click();
    page.waitForResponse(
            response -> response.url().contains("/carts") && response.request().method().equals("POST"),
            () -> {
              page.getByText("Add to cart").click();
              page.getByRole(AriaRole.ALERT).click();
            });
  }
}
