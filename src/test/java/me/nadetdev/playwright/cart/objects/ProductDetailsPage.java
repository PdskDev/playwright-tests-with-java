package me.nadetdev.playwright.cart.objects;

import com.microsoft.playwright.Page;

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
        page.getByText("Add to cart").click();
    }

    public void viewCartContent(int qty) {
        page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals(String.valueOf(qty)));
        page.getByTestId("nav-cart").click();
    }
}
