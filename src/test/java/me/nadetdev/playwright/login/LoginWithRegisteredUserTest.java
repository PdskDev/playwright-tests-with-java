package me.nadetdev.playwright.login;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import me.nadetdev.playwright.domain.User;
import me.nadetdev.playwright.fixtures.PlaywrightTestingBase;
import me.nadetdev.playwright.login.objects.LoginPage;
import me.nadetdev.playwright.apicalls.UserApiClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Login and register user")
public class LoginWithRegisteredUserTest extends PlaywrightTestingBase {

  @Test
  @Story("Login with registered user")
  @DisplayName("Should be able to login with a registered user")
  void should_login_with_registered_user() {
    // register a new user
    User newUser = User.getFakeUser();
    UserApiClient userApiClient = new UserApiClient(page);
    userApiClient.register(newUser);

    // login user via login page
    LoginPage loginPage = new LoginPage(page);
    loginPage.open();
    loginPage.loginAs(newUser);

    // Check that correct user is log in
    Assertions.assertThat(loginPage.homeAccountPageTitle()).isEqualTo("My account");
  }

  @Test
  @Story("Login with invalid password")
  @DisplayName("Should reject user if he provie an invalid password")
  void should_reject_user_with_invalid_password() {
    User invalidUser = User.getFakeUser();
    LoginPage loginPage = new LoginPage(page);
    loginPage.open();
    loginPage.loginAs(invalidUser.withPassword("wrong-password"));

    Assertions.assertThat( loginPage.loginErrorMessage()).isEqualTo("Invalid email or password");
  }
}