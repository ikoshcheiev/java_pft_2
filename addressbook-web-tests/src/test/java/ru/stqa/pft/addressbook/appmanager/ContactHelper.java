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

    public void modify(ContactData contact) {
        //initContactModification();
        //int id = contact.getId();
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", contact.getId()))).click();
        fillContactForm(contact, false);
        submitContactModification();
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
            String address = cells.get(3).getText().replaceAll("\\n", "");
            String email = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            //String[] phones = cells.get(5).getText().split("\n");
            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstname(firstName)
                    .withLastname(lastName)
                    .withAllPhones(allPhones)
                    .withAddress(address)
                    .withEmail(email));
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
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstName).withLastname(lastName)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withAddress(address).withEmail(email);
    }

    private void initContactModificationById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public ContactData infoFromDetailsPage(ContactData contact) {
        initContactDetailsOpeningById(contact.getId());
        String fullName = wd.findElement(By.cssSelector("div#content > b")).getText();
        String allPhones = wd.findElement(By.xpath("//div[@id='content']/b/..")).getText();
        String address = wd.findElement(By.xpath("//div[@id='content']/b/..")).getText();

        String email = wd.findElement(By.cssSelector("div#content > a")).getAttribute("href");
        if(!email.isEmpty()) {allPhones = allPhones.substring(0, allPhones.indexOf(wd.findElement(By.xpath("//div[@id='content']/a")).getText()));}

        if(allPhones.contains("H:")){
            allPhones = allPhones.substring(allPhones.indexOf("H: "));
            address = address.substring(address.indexOf(fullName) + fullName.length(), address.indexOf(allPhones));
            allPhones = allPhones.substring("H: ".length());
            if(allPhones.contains("M:"))allPhones = allPhones.substring(0, allPhones.indexOf("M: ")) + allPhones.substring(allPhones.indexOf("M: ") + 3);
            if(allPhones.contains("W:"))allPhones = allPhones.substring(0, allPhones.indexOf("W: ")) + allPhones.substring(allPhones.indexOf("W: ") + 3);
        }else if(allPhones.contains("M:")){
            allPhones = allPhones.substring(allPhones.indexOf("M: "));
            address = address.substring(address.indexOf(fullName) + fullName.length(), address.indexOf(allPhones));
            allPhones = allPhones.substring("M: ".length());
            if(allPhones.contains("W:"))allPhones = allPhones.substring(0, allPhones.indexOf("W: ")) + allPhones.substring(allPhones.indexOf("W: ") + 3);
        }else if(allPhones.contains("W:")) {
            allPhones = allPhones.substring(allPhones.indexOf("W: "));
            address = address.substring(address.indexOf(fullName) + fullName.length(), address.indexOf(allPhones));
            allPhones = allPhones.substring("W: ".length());
        }else allPhones = null;

        email = email.substring(email.indexOf("mailto:") + "mailto:".length());
        address = address.replaceAll("\\n", "");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFullname(fullName).withAddress(address)
                .withAllPhones(allPhones).withEmail(email);
    }

    private void initContactDetailsOpeningById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']", id))).click();
    }
}
