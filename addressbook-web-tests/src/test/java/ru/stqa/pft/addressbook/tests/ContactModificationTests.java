package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().gotoHomePage();
        if (!app.getContactHelper().isThereAContact()) {
            ContactData contact = new ContactData("first name", "second name", "test 1");
            app.getContactHelper().createContact(contact);
        }
    }

    @Test(enabled = false)
    public void testContactModification() {
        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() - 1;
        ContactData contact = new ContactData(before.get(index).getId(), "test_name", "test_surname", "test 1");
        app.getContactHelper().modifyContact(index, contact);

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);//?
        before.add(contact);//?
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertNotEquals(before, after);
    }
}
