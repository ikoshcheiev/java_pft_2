package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withName("test1");
        app.group().create(group);
        Groups after = app.group().all();

        MatcherAssert.assertThat(after.size(), equalTo(before.size() + 1));
        MatcherAssert.assertThat(after, equalTo(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt())));
    }
}