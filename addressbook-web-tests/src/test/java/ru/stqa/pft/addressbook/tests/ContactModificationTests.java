package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstname("first name").withLastname("second name").withGroup("test 1"));
            app.goTo().homePage();
        }
    }

    @Test//(enabled = false)
    public void testContactModification() {
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId( modifiedContact.getId())
                .withFirstname("first name modified")
                .withLastname("second name modified")
                .withGroup("test 1");
        app.contact().modify(contact);

        MatcherAssert.assertThat(app.contact().count(), equalTo(before.size()));

        //Contacts after = app.db().contacts();
        //before.remove(modifiedContact);
        //before.add(contact);
        //MatcherAssert.assertThat(before, equalTo(after));
        verifyContactListInUI();
    }
}
