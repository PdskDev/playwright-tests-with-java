package me.nadetdev.playwright.products;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import me.nadetdev.playwright.fixtures.ScreenManager;

import java.util.List;

public class ProductListPage {
    private final Page page;

    public ProductListPage(Page page) {
        this.page = page;
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
