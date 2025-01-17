package me.nadetdev.playwright.cucumber.stepdefinitions;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;

import java.util.Arrays;

public class PlaywrightCucumberFixtures {
    private static final ThreadLocal<Playwright> playwright =
            ThreadLocal.withInitial(
                    () -> {
                        Playwright playwright = Playwright.create();
                        playwright.selectors().setTestIdAttribute("data-test");
                        return playwright;
                    });

    private static final ThreadLocal<Browser> browser =
            ThreadLocal.withInitial(
                    () -> {
                        return playwright
                                .get()
                                .chromium()
                                .launch(
                                        new BrowserType.LaunchOptions()
                                                .setHeadless(true)
                                                .setSlowMo(100)
                                                .setArgs(
                                                        Arrays.asList(
                                                                "--no-sandbox", "--disable-extensions", "--disable-gpu")));
                    });

  private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();

  private static final ThreadLocal<Page> page = new ThreadLocal<>();

    @AfterAll
    public static void tearDown() {
        browser.get().close();
        browser.remove();
        playwright.get().close();
        playwright.remove();
    }

    @Before(order = 100)
    public static void setUpBrowserContext() {
        browserContext.set(browser.get().newContext());
        page.set(browserContext.get().newPage());
    }

    @After
    public static void closeContext() {
        //takeScreenshot();
        browserContext.get().close();
    }

    public static Page getPage() {
        return page.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContext.get();
    }
}
