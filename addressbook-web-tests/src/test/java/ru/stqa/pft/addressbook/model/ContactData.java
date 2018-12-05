package ru.stqa.pft.addressbook.model;

public class ContactData {
    private String firstname;
    private String secondname;

    public String getGroup() {
        return group;
    }

    private String group;

    public ContactData(String firstname, String secondname, String group) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.group = group;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }
}
