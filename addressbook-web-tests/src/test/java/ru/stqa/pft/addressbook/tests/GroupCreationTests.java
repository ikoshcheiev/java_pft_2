package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("test 1", null, null);
        app.getGroupHelper().createGroup(group);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);

        //l4_m8
        /*int max = 0;
        for (GroupData g : after) {
            if(g.getId() > max) {
                max = g.getId();
            }
        }*/

        //l4_m9

        //Comparator<? super GroupData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());

        /*
        Comparator<? super GroupData> byId = new Comparator<GroupData>() {
            @Override
            public int compare(GroupData o1, GroupData o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        };
        int max = after.stream().max(byId).get().getId();
        */

        //int max = after.stream().max(((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))).get().getId();
        //group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        group.setId(after.stream().max((Comparator.comparingInt(GroupData::getId))).get().getId());
        before.add(group);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}