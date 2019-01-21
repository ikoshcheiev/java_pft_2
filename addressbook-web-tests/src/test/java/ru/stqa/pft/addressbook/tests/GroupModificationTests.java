package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));

        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData()
                .withId(modifiedGroup.getId())
                .withName("Group test 1 - MODIFIED")
                .withHeader("Test2 - MODIFIED")
                .withFooter("Test3 - MODIFIED");
        app.goTo().groupPage();
        app.group().modify(group);
        MatcherAssert.assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.db().groups();
        MatcherAssert.assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }
}
