package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

public class TestBase {
    //l3_m5

    protected static final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);
    //protected static final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);
    //protected static final ApplicationManager app = new ApplicationManager(BrowserType.IE);

    @BeforeSuite
    public void setUp() {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

}
