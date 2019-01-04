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
        List<WebElement> rows = wd.findElements(By.name("entry"));
        String groupName = new Select(wd.findElement(By.name("to_group"))).getFirstSelectedOption().getText();

        for (WebElement row : rows) {
            List<WebElement> cells = wd.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
            String firstName = cells.get(1).getText();
            String lastName = cells.get(2).getText();
            String allPhones = cells.get(5).getText();
            //String[] phones = cells.get(5).getText().split("\n");
            contacts.add(new ContactData().withId(id).withFirstname(firstName).withLastname(lastName).withAllPhones(allPhones));
        }
        return contacts;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstName).withLastname(lastName)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }

    private void initContactModificationById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
        /*WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../..")); // однимаемся на 2ой элемент вверх
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();*/

        //wd.findElement(By..cssSelector(String.format("input[value='%s']"/../../td[8]/a, id))) // 8 потому что нумерация с 1 а не с 0
    }

    public void modify(ContactData contact) {
        //initContactModification();
        //int id = contact.getId();
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", contact.getId()))).click();
        fillContactForm(contact, false);
        submitContactModification();
        returnToHomePage();
    }
}
