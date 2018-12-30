package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test//(enabled = false)
    public void testContactCreation() {
        app.goTo().homePage();
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData()
                .withFirstname("first name")
                .withSecondname("second name")
                .withGroup("test 1");
        app.contact().create(contact);

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        int max = after.stream().max(byId).get().getId();
        contact.withId(max);
        before.add(contact);
        before.sort(byId);//Collections.sort(before)
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
