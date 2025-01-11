package me.nadetdev.playwright.search;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import me.nadetdev.playwright.fixtures.PlaywrightTestingBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SearchProductPageObjectTest extends PlaywrightTestingBase {

    @BeforeEach
    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("Without Page Objects")
    @Test
    void withoutPageObjects() {
        page.waitForResponse("**/products/search?q=tape", () -> {
            page.getByPlaceholder("Search").fill("tape");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
        });
        List<String> matchingProducts = page.getByTestId("product-name").allInnerTexts();
        Assertions.assertThat(matchingProducts)
                .contains("Tape Measure 7.5m", "Measuring Tape", "Tape Measure 5m");
    }

    @DisplayName("With Page Objects")
    @Test
    void withPageObjects(){}



}
