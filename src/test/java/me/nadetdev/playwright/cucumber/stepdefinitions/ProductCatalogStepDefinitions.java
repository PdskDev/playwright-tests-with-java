package me.nadetdev.playwright.cucumber.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import me.nadetdev.playwright.fixtures.ProductSummary;
import me.nadetdev.playwright.navbar.objects.NavBarComponent;
import me.nadetdev.playwright.products.ProductListPage;
import me.nadetdev.playwright.search.objects.SearchComponent;
import org.assertj.core.api.Assertions;

public class ProductCatalogStepDefinitions {
  NavBarComponent navBar;
  SearchComponent searchComponent;
  ProductListPage productListPage;

  @Before
  public void setuppageObjects() {
    navBar = new NavBarComponent(PlaywrightCucumberFixtures.getPage());
    searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
    productListPage = new ProductListPage(PlaywrightCucumberFixtures.getPage());
  }

  @Given("Sally is on the home page")
  public void sally_is_on_the_home_page() {
    navBar.openHome();
  }

  @When("Sally searches for an {string}")
  public void sally_searches_for_an(String serchTerm) {
    searchComponent.searchBy(serchTerm);
  }

  @Then("the {string} product should be displayed")
  public void the_product_should_be_displayed(String productName) {
    var matchingProducts = productListPage.getProductNames();
    Assertions.assertThat(matchingProducts).contains(productName);
  }

  @Then("the following products should be displayed:")
  public void theFollowingProductsShouldBeDisplayed(List<String> expectedProducts) {
    var matchingProducts = productListPage.getProductNames();
    Assertions.assertThat(matchingProducts).containsAll(expectedProducts);
  }

  @Then("the following products should be displayed with his prices:")
  public void theFollowingProductsShouldBeDisplayedWithHisPrices(DataTable expectedProductsDataTable) {
    List<Map<String, String>> expectedProductsData =
            expectedProductsDataTable.asMaps();

    List<ProductSummary> expectedProductsList =
            expectedProductsData.stream()
                    .map(product -> new ProductSummary(product.get("Product"), product.get("Price")))
                    .toList();

    List<ProductSummary> matchingProductsOnPage = productListPage.getProductsSummaries();

    Assertions.assertThat(matchingProductsOnPage).containsExactlyInAnyOrderElementsOf(expectedProductsList);
  }

  @DataTableType
  public ProductSummary productSummaryRow(Map<String, String> productData) {
    return new ProductSummary(productData.get("Product"), productData.get("Price"));
  }

  @Then("result page should displays the following products and prices:")
  public void resultPageShouldDisplaysTheFollowingProductsAndPrices(List<ProductSummary> expectedProductsDataTable) {
    List<ProductSummary> matchingProductsOnPage = productListPage.getProductsSummaries();
    Assertions.assertThat(matchingProductsOnPage).containsAnyElementsOf(expectedProductsDataTable);
  }

  @Then("no product should be displayed")
  public void noProductShouldBeDisplayed() {
    List<ProductSummary> matchingProductsOnPage = productListPage.getProductsSummaries();
    Assertions.assertThat(matchingProductsOnPage).isEmpty();
  }

  @And("the message {string} should be displayed")
  public void theMessageShouldBeDisplayed(String message) {
    String completionMessage = productListPage.noProductsFound();
    Assertions.assertThat(completionMessage).contains(message);
  }

  @And("she filters by {string}")
  public void sheFiltersBy(String filter) {
    searchComponent.filterBy(filter);
  }

  @When("she sorts by {string}")
  public void sheSortsBy(String sortFilter) {
    searchComponent.sortBy(sortFilter);
  }

  @Then("the first product displayed should be {string}")
  public void theFirstProductDisplayedShouldBe(String firstProductName) {
    List<String> productNames = productListPage.getProductNames();
    Assertions.assertThat(productNames).startsWith(firstProductName);
  }
}
