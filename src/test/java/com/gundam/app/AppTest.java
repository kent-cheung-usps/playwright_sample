package com.gundam.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
@UsePlaywright
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
    	try (Playwright playwright = Playwright.create()) {
    	      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
    	        .setHeadless(false));
    	      BrowserContext context = browser.newContext();
    	      Page page = context.newPage();
    	      page.navigate("https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&emr=1&followup=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&ifkv=AeZLP9_RPyHjc6ZxTkaASC7X66tYDsWiVzxG2xmnN_uXsdz0j_qfudK-G8B1Mly3HEz1JToxArmnpg&osid=1&passive=1209600&service=mail&flowName=GlifWebSignIn&flowEntry=ServiceLogin&dsh=S37105020%3A1734309634897359&ddm=1");
    	      page.getByLabel("Email or phone").fill("two.tester2@gmail.com");
    	      page.getByLabel("Email or phone").press("Enter");
    	    }
    	  }
}
