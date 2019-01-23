package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstname("first name").withLastname("second name").withGroup("test 1"));
            app.goTo().homePage();
        }
    }

    @Test//(enabled = false)
    public void testContactDeletion() {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);

        MatcherAssert.assertThat(app.contact().count(), equalTo(before.size() - 1));

        //ontacts after = app.db().contacts();
       //MatcherAssert.assertThat(after, equalTo(before.without(deletedContact)));
        verifyContactListInUI();
    }
}
