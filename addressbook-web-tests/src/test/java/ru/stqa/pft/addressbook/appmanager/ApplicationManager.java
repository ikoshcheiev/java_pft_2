package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    WebDriver wd;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private SessionHelper sessionHelper;
    private String browser;
    private ContactHelper contactHelper;
    private DbHelper dbHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        this.properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local"); // в качестве дефолтного значения для target будет использоваться local
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        dbHelper = new DbHelper();

        if (browser.equals(BrowserType.FIREFOX)){
            System.setProperty("webdriver.gecko.driver", "C:\\toolsAutomation\\geckodriver-v0.20.1-win64\\geckodriver.exe");
            // geckodriver-v0.21.0-win64.zip works only with Firefox v.57+
            wd = new FirefoxDriver();
        } else if(browser.equals(BrowserType.CHROME)){
            wd = new ChromeDriver();
        } else if(browser.equals(BrowserType.IE)){
            wd = new InternetExplorerDriver();
        }

        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wd.get(properties.getProperty("web.baseUrl")); //"http://localhost/addressbook/");
        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void stop() {
        wd.quit();
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public DbHelper db() {
        return dbHelper;
    }
}