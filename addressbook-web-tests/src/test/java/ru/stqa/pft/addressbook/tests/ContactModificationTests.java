package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstname("first name").withLastname("second name"));
            app.goTo().homePage();
        }
    }

    @Test//(enabled = false)
    public void testContactModification() {
        Groups groups = app.db().groups();

        app.goTo().homePage();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId( modifiedContact.getId())
                .withFirstname("first name modified")
                .withLastname("second name modified")
                .inGroup(groups.iterator().next());
        app.contact().modify(contact);

        MatcherAssert.assertThat(app.contact().count(), equalTo(before.size()));

        //Contacts after = app.db().contacts();
        //before.remove(modifiedContact);
        //before.add(contact);
        //MatcherAssert.assertThat(before, equalTo(after));
        verifyContactListInUI();
    }

/*    @Test//(enabled = false)
    public void testAddingContactToGroup() {
        app.goTo().homePage();
        Contacts contacts = app.contact().all();


        if(app.db().) {

        }

        ContactData contact = contacts.iterator().next();
        if(contact.getGroups().size() != 0){
            Assert.assertTrue(contact.getGroups().size() < app.db().groups().size());
            app.contact().addToAvailableGroup(contact, app.db().groups());
        }
        else {
            app.contact().addToAvailableGroup(contact, app.db().groups());
        }
        //надо проверять спиоок групп в которых он уже добавлен и добавлять в которую не добавлен...или в любую
    }

    @Test//(enabled = false)
    public void testRemovingContactFromGroup() {
        app.goTo().homePage();

    }*/
}
