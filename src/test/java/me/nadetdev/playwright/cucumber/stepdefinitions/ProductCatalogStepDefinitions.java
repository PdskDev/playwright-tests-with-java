package me.nadetdev.playwright.cucumber.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import me.nadetdev.playwright.navbar.objects.NavBarComponent;
import me.nadetdev.playwright.products.ProductListPage;
import me.nadetdev.playwright.search.objects.SearchComponent;
import org.assertj.core.api.Assertions;

public class ProductCatalogStepDefinitions {
    NavBarComponent navBar;
    SearchComponent searchComponent;
    ProductListPage productListPage;


    @Before
    public void setuppageObjects(){
        navBar = new NavBarComponent(PlaywrightCucumberFixtures.getPage());
        searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
        productListPage = new ProductListPage(PlaywrightCucumberFixtures.getPage());
    }

    @Given("Sally is on the home page")
    public void sally_is_on_the_home_page() {
        navBar.openHome();
    }

    @When("Sally searchs for an {string}")
    public void sally_searchs_for_an(String serchTerm) {
        searchComponent.searchBy(serchTerm);
    }

    @Then("the {string} product should be displayed")
    public void the_product_should_be_displayed(String productName) {
        var matchingProducts = productListPage.getProductNames();
        Assertions.assertThat(matchingProducts).contains(productName);
    }
}
