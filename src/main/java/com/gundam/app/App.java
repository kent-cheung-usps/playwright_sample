package com.gundam.app;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://cop-sit.usps.com/");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log In")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create New Account")).click();
            page.getByPlaceholder("Email Address").click();
            page.getByPlaceholder("Email Address").fill("chaosbusters@usps.gov");
            page.getByPlaceholder("Email Address").press("Enter");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
            
            assertThat(page.getByRole(AriaRole.HEADING,
                    new Page.GetByRoleOptions().setName("Create Your USPS.com Business"))).isVisible();

            // Assert the text "With a business account, you" is visible
            assertThat(page.getByText("With a business account, you")).isVisible();

            Thread.sleep(10000);

        }
    }
}