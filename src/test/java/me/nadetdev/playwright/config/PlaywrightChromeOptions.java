package me.nadetdev.playwright.config;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.util.Arrays;

public class PlaywrightChromeOptions implements OptionsFactory {

  @Override
  public Options getOptions() {
    return new Options()
        .setTestIdAttribute("data-test")
        .setLaunchOptions(
            new BrowserType.LaunchOptions()
                .setHeadless(true)
                //.setSlowMo(100)
                .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions")));
  }
}
