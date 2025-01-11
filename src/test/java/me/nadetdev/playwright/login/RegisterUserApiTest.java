package me.nadetdev.playwright.login;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import me.nadetdev.playwright.config.HeadlessChromeOptions;
import me.nadetdev.playwright.domain.User;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@UsePlaywright(HeadlessChromeOptions.class)
public class RegisterUserApiTest {

    private static APIRequestContext apiRequestContext;

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
    void tearDown(){
        if(apiRequestContext != null) {
            apiRequestContext.dispose();
        }
    }

    @Test
    void shouldRegisterNewUser(){
        User validUser = User.getFakeUser();

        var response = apiRequestContext.post("/users/register",
               RequestOptions.create()
                       .setHeader("Content-Type", "application/json")
                       .setData(validUser));

       //Assertions.assertThat(response.status()).isEqualTo(201);

        String repsonseBody = response.text();
        Gson gson = new Gson();
        User createdUser = gson.fromJson(repsonseBody, User.class);

        Assertions.assertThat(createdUser).isEqualTo(validUser.withPassord(null));

        SoftAssertions.assertSoftly(softly ->
                softly.assertThat(response.status())
                        .as("Registration should return 201 created status code")
                        .isEqualTo(201)
        );
}


}
