package me.nadetdev.playwright.navbar.objects;

import com.microsoft.playwright.Page;

public class NavBar {
    private final Page page;

    public NavBar(Page page) {
        this.page = page;
    }

    public void openCart() {
        page.getByTestId("nav-cart").click();
    }

    public void openCartByQty(int qty) {
        page.waitForCondition(() ->
                page.getByTestId("cart-quantity").textContent().equals(String.valueOf(qty)));
        page.getByTestId("nav-cart").click();

    /*page.waitForResponse(
        response -> response.url().contains("/carts") && response.request().method().equals("GET"),
        () -> {
          page.getByText("Add to cart").click();
          page.getByRole(AriaRole.ALERT).click();
        });*/
    }
}
