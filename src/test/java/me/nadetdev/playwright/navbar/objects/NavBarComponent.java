package me.nadetdev.playwright.navbar.objects;

import com.microsoft.playwright.Page;

public class NavBarComponent {
    private final Page page;

    public NavBarComponent(Page page) {
        this.page = page;
    }

    public void openCart() {
        page.getByTestId("nav-cart").click();
    }

    /*public void openCartByQty(int qty) {
        page.waitForCondition(() ->
                page.getByTestId("cart-quantity").textContent().equals(String.valueOf(qty)));
        page.getByTestId("nav-cart").click();
    }*/
}
