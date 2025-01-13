package me.nadetdev.playwright.contact;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import me.nadetdev.playwright.config.PlaywrightChromeOptions;
import me.nadetdev.playwright.tutos.AlterSimpleTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(PlaywrightChromeOptions.class)
public class ContactFormTest {

  @BeforeEach
  void setUp(Page page) {
    page.navigate("https://practicesoftwaretesting.com/contact");
    page.waitForLoadState(LoadState.NETWORKIDLE);
  }

  @DisplayName("Interact with text fields")
  @Test
  void whenInteractWithTextFields(Page page) throws URISyntaxException {
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

  @DisplayName("Interact with mandatory fields")
  @Test
  void whenInteractWithMandatoryFields(Page page) {
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

  @DisplayName("Interact with mandatory fields")
  @ParameterizedTest
  @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
  void whenInteractWithMandatoryFieldsParameterized(String fieldName, Page page) {
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
