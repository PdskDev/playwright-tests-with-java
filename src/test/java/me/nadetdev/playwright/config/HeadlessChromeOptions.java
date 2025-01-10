package me.nadetdev.playwright.config;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.util.Arrays;

public class HeadlessChromeOptions implements OptionsFactory {

  @Override
  public Options getOptions() {
    return new Options()
        .setHeadless(false)
        .setTestIdAttribute("data-test")
        .setLaunchOptions(
            new BrowserType.LaunchOptions()
                .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions")));
  }
}
