package me.nadetdev.playwright.cart.objects;

import com.microsoft.playwright.Page;

import java.util.List;

public class CheckoutPage {
  private final Page page;

  public CheckoutPage(Page page) {
    this.page = page;
  }

  public List<CartLineItem> getLineItems() {
    page.locator("app-cart tbody tr").first().waitFor();
    return page.locator("app-cart tbody tr").all().stream()
        .map(
            row -> {
              String title = trimmed(row.getByTestId("product-title").innerText());
              int quantity = Integer.parseInt(row.getByTestId("product-quantity").inputValue());
              double price =
                  Double.parseDouble(
                      cleanCurrencySymbol(row.getByTestId("product-price").innerText()));
              double total =
                  Double.parseDouble(
                      cleanCurrencySymbol(row.getByTestId("line-price").innerText()));
              return new CartLineItem(title, quantity, price, total);
            })
        .toList();
  }

  private String cleanCurrencySymbol(String value) {
    return value.replace("$", "");
  }

  private String trimmed(String value) {
    return value.strip().replaceAll("\u00A0", "");
  }
}
