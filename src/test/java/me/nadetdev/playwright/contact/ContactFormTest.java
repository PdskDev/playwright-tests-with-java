package me.nadetdev.playwright.contact;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import me.nadetdev.playwright.config.PlaywrightChromeOptions;
import me.nadetdev.playwright.fixtures.PlaywrightTestingBase;
import me.nadetdev.playwright.tutos.AlterSimpleTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
@Feature("Contact form")
public class ContactFormTest extends PlaywrightTestingBase {

  @BeforeEach
  void setUp() {
    page.navigate("https://practicesoftwaretesting.com/contact");
    page.waitForLoadState(LoadState.NETWORKIDLE);
  }


  @Test
  @Story("Interact with text fields")
  @DisplayName("Interact with text fields")
  void whenInteractWithTextFields() throws URISyntaxException {
    var firstNameField = page.getByLabel("First name");
    var lastNameField = page.getByLabel("Last name");
    var emailAddressField = page.getByLabel("Email");
    var messageField = page.getByLabel("Message");
    var subjectField = page.getByLabel("Subject");
    var uploadField = page.getByLabel("Attachment");

    firstNameField.fill("NadetDev");
    lastNameField.fill("Testman");
    emailAddressField.fill("test@email.com");
    // subjectField.selectOption("Webmaster");
    // subjectField.selectOption(new SelectOption().setLabel("Payments"));
    // subjectField.selectOption(new SelectOption().setIndex(3));
    subjectField.selectOption(new SelectOption().setValue("warranty"));
    messageField.fill("This is a e2e test with Playwright");

    Path fileToUpload = Paths.get(ClassLoader.getSystemResource("data/sample-data.txt").toURI());
    page.setInputFiles("#attachment", fileToUpload);
    String uploadedFile = uploadField.inputValue();

    assertThat(firstNameField).hasValue("NadetDev");
    assertThat(lastNameField).hasValue("Testman");
    assertThat(emailAddressField).hasValue("test@email.com");
    assertThat(subjectField).hasValue("warranty");
    assertThat(messageField).hasValue("This is a e2e test with Playwright");
    Assertions.assertThat(uploadedFile).endsWith("sample-data.txt");
  }

  @Test
  @Story("Interact with mandatory fields 1")
  @DisplayName("Interact with mandatory fields 1")
  void whenInteractWithMandatoryFields() {
    var firstNameField = page.getByLabel("First name");
    var lastNameField = page.getByLabel("Last name");
    var emailAddressField = page.getByLabel("Email");
    var messageField = page.getByLabel("Message");
    var subjectField = page.getByLabel("Subject");
    var sendButton = page.getByText("Send");

    sendButton.click();

    var errorMessage = page.getByRole(AriaRole.ALERT).getByText("First name is required");

    assertThat(errorMessage).isVisible();
  }

  @Story("Interact with mandatory fields 2")
  @DisplayName("Interact with mandatory fields 2")
  @ParameterizedTest
  @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
  void whenInteractWithMandatoryFieldsParameterized(String fieldName) {
    var firstNameField = page.getByLabel("First name");
    var lastNameField = page.getByLabel("Last name");
    var emailAddressField = page.getByLabel("Email");
    var messageField = page.getByLabel("Message");
    var subjectField = page.getByLabel("Subject");
    var sendButton = page.getByText("Send");

    // Fill in field values
    firstNameField.fill("NadetDev");
    lastNameField.fill("Testman");
    emailAddressField.fill("test@email.com");
    messageField.fill("This is a e2e test with Playwright");
    subjectField.selectOption(new SelectOption().setLabel("Payments"));

    // Clear one field
    page.getByLabel(fieldName).clear();

    sendButton.click();

    var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");

    assertThat(errorMessage).isVisible();
  }
}
