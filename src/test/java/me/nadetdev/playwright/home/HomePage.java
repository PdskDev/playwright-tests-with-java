package me.nadetdev.playwright.home;

import com.microsoft.playwright.Page;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void open() {
        page.navigate("https://practicesoftwaretesting.com");
    }
}
