package com.gundam.app;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://cop-sit.usps.com/");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log In")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create New Account")).click();
            page.getByPlaceholder("Email Address").click();
            page.getByPlaceholder("Email Address").fill("chaosbusters@usps.gov");
            page.getByPlaceholder("Email Address").press("Enter");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
            
            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Create Your USPS.com Business"))).isVisible();
            assertThat(page.getByText("With a business account, you")).isVisible();

            Thread.sleep(60000);
                        
            String scriptPath = "src/main/resources/extractValidationLink.py";
            // Check if the file exists
            File scriptFile = new File(scriptPath);
            if (!scriptFile.exists()) {
                System.err.println("Python script not found at: " + scriptPath);
                return; // Exit the program if the file is missing
            }
            
            // Execute the Python script extractValidationLink.py and capture the output URL
            String url = executePythonScript(scriptPath);
            System.out.println("URL = " + url);
            if (url != null && !url.isEmpty()) {
                page.navigate(url);
                
                Thread.sleep(10000);
                
            } else {
                System.out.println("Python script did not return a valid URL.");
            }
        }
    }
    
    // Method to execute a Python script and capture its output
    private static String executePythonScript(String scriptPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                System.err.println("Python script execution failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
