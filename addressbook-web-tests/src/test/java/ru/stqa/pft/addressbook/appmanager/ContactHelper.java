package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getSecondname());
        //l3_m9
        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText("Group test 1 - MODIFIED");
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void returnToHomePage() {
        click(By.linkText("home"));
    }

    public void initContactModification() {
        click(By.cssSelector("img[alt='Edit']"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContactById(int id) {
        //click(By.name("selected[]")); // By.xpath("//form[@id='LoginForm']/input[3]")
        //wd.findElements(By.name("selected[]")).get(index).click();
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//div/div[4]/form[2]/div[2]/input"));
        wd.switchTo().alert().accept();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification();
        fillContactForm(contact, false);
        submitContactModification();
        returnToHomePage();
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        returnToHomePage();
    }

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<>();
        List<WebElement> tr = wd.findElements(By.xpath("//table//tr[@name=\"entry\"]"));
        String groupName = new Select(wd.findElement(By.name("to_group"))).getFirstSelectedOption().getText();

        for (WebElement element : tr) {
            String firstName = element.findElement(By.xpath("td[3]")).getText();
            String lastName = element.findElement(By.xpath("td[2]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            contacts.add(new ContactData().withId(id).withFirstname(firstName).withSecondname(lastName).withGroup(groupName));
        }
        return contacts;
    }
}
