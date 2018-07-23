package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreatiom() {

        gotoGroupPage();
        initGroupCreation();

        fillGroupForm(new GroupData("Group test 1", "Test2", "Test3"));
        submitGroupCreation();

        returnToGroupPage();
    }

}