package me.nadetdev.playwright.login;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import me.nadetdev.playwright.config.HeadlessChromeOptions;
import me.nadetdev.playwright.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.SoftAssertions.*;

@UsePlaywright(HeadlessChromeOptions.class)
public class RegisterUserApiTest {

  private static APIRequestContext apiRequestContext;
  private final Gson gson = new Gson();

  @BeforeEach
  void setupRequestContext(Playwright playwright) {
    apiRequestContext =
        playwright
            .request()
            .newContext(
                new APIRequest.NewContextOptions()
                    .setBaseURL("https://api.practicesoftwaretesting.com")
                    .setExtraHTTPHeaders(
                        new HashMap<>() {
                          {
                            put("Accept", "application/json");
                          }
                        }));
  }

  @AfterEach
  void tearDown() {
    if (apiRequestContext != null) {
      apiRequestContext.dispose();
    }
  }

  @Test
  void shouldRegisterNewUser() {
    User validUser = User.getFakeUser();

    var response =
        apiRequestContext.post(
            "/users/register",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(validUser));

    // Assertions.assertThat(response.status()).isEqualTo(201);

    String responseBody = response.text();
    User createdUser = gson.fromJson(responseBody, User.class);
    JsonObject responseObject = gson.fromJson(responseBody, JsonObject.class);

    // Assertions.assertThat(createdUser).isEqualTo(validUser.withPassord(null));

    assertSoftly(
        softly -> {
          softly
              .assertThat(response.status())
              .as("Registration should return 201 created status code")
              .isEqualTo(201);

          softly
              .assertThat(createdUser)
              .as("Created user should match the specified user without password")
              .isEqualTo(validUser.withPassword(null));

          softly
              .assertThat(responseObject.has("password"))
              .as("No password should be returned")
              .isFalse();

          softly
              .assertThat(responseObject.get("id").getAsString())
              .as("Registered user should have an id")
              .isNotEmpty();

          softly
              .assertThat(response.headers().get("content-type"))
              .as("Header contains application/json")
              .contains("application/json");
        });
  }

  @Test
  void first_name_is_mandatory() {
    User userWithoutFirstname = User.getFakeUserWithNoFistName();
    var response =
        apiRequestContext.post(
            "/users/register",
            RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(userWithoutFirstname));

    assertSoftly(
        softly -> {
          softly
              .assertThat(response.status())
              .as("Registration should return 422 created status code")
              .isEqualTo(422);

          JsonObject responseObject = gson.fromJson(response.text(), JsonObject.class);

          softly.assertThat(responseObject.has("first_name")).isTrue();

          String errorMessage = responseObject.get("first_name").getAsString();

          softly.assertThat(errorMessage).isEqualTo("The first name field is required.");
        });
  }
}
