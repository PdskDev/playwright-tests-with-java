package me.nadetdev.playwright.search.objects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import me.nadetdev.playwright.fixtures.ScreenManager;

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
      ScreenManager.takeScreenshot(page, "Search item");
  }

  @Step("Clear search")
  public void clearSearch() {
    page.waitForResponse("**/products?between**", () -> page.getByTestId("search-reset").click());
      ScreenManager.takeScreenshot(page, "Clear seach");
  }

  @Step("Clear filter")
  public void filterBy(String filterName) {
    page.waitForResponse("**/products?**by_category**", () -> page.getByLabel(filterName).click());
  }
}
