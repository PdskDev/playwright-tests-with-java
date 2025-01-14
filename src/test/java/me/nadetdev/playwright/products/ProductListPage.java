package me.nadetdev.playwright.products;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.List;

public class ProductListPage {
    private final Page page;

    public ProductListPage(Page page) {
        this.page = page;
    }

    @Step("Get product names")
    public List<String> getProductNames() {
        return page.getByTestId("product-name").allInnerTexts();
    }

    @Step("View product details")
    public void viewProductDetails(String productName) {
        page.locator(".card").getByText(productName).click();
    }

    @Step("No products found")
    public String noProductsFound() {
        page.getByTestId("search_completed").waitFor();
        return page.getByTestId("search_completed").innerText();
    }
}
