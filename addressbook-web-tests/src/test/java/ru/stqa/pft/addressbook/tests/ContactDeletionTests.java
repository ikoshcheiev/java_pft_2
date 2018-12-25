package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion(){
        app.getNavigationHelper().gotoHomePage();
        if(!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().initContactCreation();
            app.getContactHelper().fillContactForm(new ContactData("first name", "second name", "test 1"), true);
            app.getContactHelper().submitContactCreation();
            app.getContactHelper().returnToHomePage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().returnToHomePage();
    }
}
