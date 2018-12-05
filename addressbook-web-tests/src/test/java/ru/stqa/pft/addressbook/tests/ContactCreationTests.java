package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    //l3_m8

    @Test
    public void testContactCreation() {

        app.getContactHelper().initContactCreation();

        app.getContactHelper().fillContactForm(new ContactData("first name", "second name", "test 1"), true);
        app.getContactHelper().submitContactCreation();

        app.getContactHelper().returnToHomePage();
    }
}
