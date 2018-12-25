package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testGroupModification(){
        app.getNavigationHelper().gotoHomePage();
        if(!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().initContactCreation();
            app.getContactHelper().fillContactForm(new ContactData("first name", "second name", "test 1"), true);
            app.getContactHelper().submitContactCreation();
            app.getContactHelper().returnToHomePage();
        }
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("test_name", "test_surname", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
    }
}
