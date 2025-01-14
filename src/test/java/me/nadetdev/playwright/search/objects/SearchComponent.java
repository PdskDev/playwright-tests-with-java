package me.nadetdev.playwright.search.objects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

public class SearchComponent {
  private final Page page;

  public SearchComponent(Page page) {
    this.page = page;
  }

  @Step("Search for {searchTerm}")
  public void searchBy(String searchTerm) {
    page.waitForResponse(
        "**/products/search?q=" + searchTerm,
        () -> {
          page.getByPlaceholder("Search").fill(searchTerm);
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
  }

  @Step("Clear search")
  public void clearSearch() {
    page.waitForResponse("**/products?between**", () -> page.getByTestId("search-reset").click());
  }
}
