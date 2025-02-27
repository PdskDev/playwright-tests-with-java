package me.nadetdev.playwright.navbar.objects;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import me.nadetdev.playwright.fixtures.ScreenManager;

public class NavBarComponent {
    private final Page page;

    public NavBarComponent(Page page) {
        this.page = page;
    }

    @Step("Open shopping cart page")
    public void openCart() {
        page.getByTestId("nav-cart").click();
        ScreenManager.takeScreenshot(page, "Open shopping cart");
    }

    @Step("Open home page")
    public void openHome() {
        page.navigate("https://practicesoftwaretesting.com");
        page.getByTestId("nav-home").click();
        ScreenManager.takeScreenshot(page, "Open home page");
    }

    /*public void openCartByQty(int qty) {
        page.waitForCondition(() ->
                page.getByTestId("cart-quantity").textContent().equals(String.valueOf(qty)));
        page.getByTestId("nav-cart").click();
    }*/
}
