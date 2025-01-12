package me.nadetdev.playwright.products;

import com.microsoft.playwright.Page;

import java.util.List;

public class ProductListPage {
    private final Page page;

    public ProductListPage(Page page) {
        this.page = page;
    }

    public List<String> getProductNames() {
        return page.getByTestId("product-name").allInnerTexts();
    }

    public void viewProductDetails(String productName) {
        page.locator(".card").getByText(productName).click();
    }

    public String noProductsFound() {
        page.getByTestId("search_completed").waitFor();
        return page.getByTestId("search_completed").innerText();
    }
}
