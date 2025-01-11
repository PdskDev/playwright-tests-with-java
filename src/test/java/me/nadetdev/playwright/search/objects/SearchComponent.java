package me.nadetdev.playwright.search.objects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchComponent {
    private final Page page;

    public SearchComponent(Page page) {
        this.page = page;
    }

    public void searchBy(String searchTerm) {
        page.waitForResponse("**/products/search?q="+searchTerm, () -> {
        page.getByPlaceholder("Search").fill(searchTerm);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
    });
}
}
