package me.nadetdev.playwright.login.objects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.RequestOptions;
import me.nadetdev.playwright.domain.User;

public class UserApiClient {
  private static final String REGISTER_USER_URL =
      "https://api.practicesoftwaretesting.com/users/register";

  private final Page page;

  public UserApiClient(Page page) {
    this.page = page;
  }

  public void register(User newUser) {
    var response =
        page.request()
            .post(
                REGISTER_USER_URL,
                RequestOptions.create()
                    .setData(newUser)
                    .setHeader("Content-Type", "application/json")
                    .setHeader("accept", "application/json"));

    if (response.status() != 201) {
      throw new IllegalStateException("Coul not create user: " + response.status());
    }
  }
}
