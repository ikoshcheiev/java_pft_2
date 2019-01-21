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
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("first name").withLastname("second name").withGroup("test 1"));
        }
    }

    @Test//(enabled = false)
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId( modifiedContact.getId())
                .withFirstname("first name modified")
                .withLastname("second name modified")
                .withGroup("test 1");
        app.contact().modify(contact);

        Contacts after = app.contact().all();
        MatcherAssert.assertThat(app.contact().count(), equalTo(before.size()));

        before.remove(modifiedContact);
        before.add(contact);
        MatcherAssert.assertThat(before, equalTo(after));
    }
}
