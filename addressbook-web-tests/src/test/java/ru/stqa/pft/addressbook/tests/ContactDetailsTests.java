package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;

public class ContactDetailsTests extends TestBase {

    //Doesn't WORK

    @Test
    public void testContactDetails(){
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactDetailsFromDetailsPage = app.contact().infoFromDetailsPage(contact);

        MatcherAssert.assertThat(mergePhones(contact), equalTo(mergePhones(contactDetailsFromDetailsPage)));
        MatcherAssert.assertThat(contact.getFullName(), equalTo(contactDetailsFromDetailsPage.getFullName()));
        contact.getAddress();
        contactDetailsFromDetailsPage.getAddress();
        MatcherAssert.assertThat(contact.getAddress(), equalTo(contactDetailsFromDetailsPage.getAddress()));
        MatcherAssert.assertThat(contact.getEmail(), equalTo(contactDetailsFromDetailsPage.getEmail()));
    }

    private String mergePhones(ContactData contact) {
        if(contact.getAllPhones() != null){
            return Arrays.asList(contact.getAllPhones().split("\n"))
                    .stream().filter((s) -> ! s.equals(""))
                    .map(ContactDetailsTests::cleaned)
                    .collect(Collectors.joining("\n"));
        }else
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactDetailsTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone){
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
