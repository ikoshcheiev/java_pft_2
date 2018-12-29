package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id;
    private final String firstname;
    private final String secondname;
    private final String group;

    public ContactData(String firstname, String secondname, String group) {
        this.id = Integer.MAX_VALUE;
        this.firstname = firstname;
        this.secondname = secondname;
        this.group = group;
    }

    public ContactData(int id, String firstname, String secondname, String group) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getGroupName() {
        return group;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return secondname != null ? secondname.equals(that.secondname) : that.secondname == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (secondname != null ? secondname.hashCode() : 0);
        return result;
    }
}
