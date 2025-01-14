package me.nadetdev.playwright.login.objects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import me.nadetdev.playwright.domain.User;
import me.nadetdev.playwright.fixtures.ScreenManager;

public class LoginPage {
  private final Page page;

  public LoginPage(Page page) {
    this.page = page;
  }

  public void open() {
    page.navigate("https://practicesoftwaretesting.com/auth/login");
    ScreenManager.takeScreenshot(page, "Open login page");
  }

  public void loginAs(User user) {
    page.getByPlaceholder("Your email").fill(user.email());
    page.getByPlaceholder("Your password").fill((user.password()));
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
    ScreenManager.takeScreenshot(page, "User login");
  }

  public String homeAccountPageTitle() {
    page.waitForCondition(() -> page.getByTestId("page-title").isVisible());
    ScreenManager.takeScreenshot(page, "User account page");
    return page.getByTestId("page-title").textContent();
  }

  public String loginErrorMessage() {
    page.waitForCondition(() -> page.getByTestId("login-error").isVisible());
    ScreenManager.takeScreenshot(page, "Login error message");
    return page.getByTestId("login-error").textContent();
  }
}
