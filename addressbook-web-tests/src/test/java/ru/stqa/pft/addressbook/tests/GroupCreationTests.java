package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        // closing stream is automated in try-with-resources
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))){
            String xml = "";
            String line = reader.readLine();
            while(line != null){
                xml += line;
                line = reader.readLine();
            }
            XStream xStream = new XStream();
            xStream.processAnnotations(GroupData.class);
            List<GroupData> groups = (List<GroupData>) xStream.fromXML(xml);
            return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))){
            String json = "";
            String line = reader.readLine();
            while(line != null){
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>(){}.getType()); // ~ List<GroupData>.class
            return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().groupPage();
    }

    @Test(dataProvider = "validGroupsFromJson")
    public void testGroupCreation(GroupData group) {
        Groups before = app.db().groups();
        //app.goTo().groupPage();
        app.group().create(group);
        MatcherAssert.assertThat(app.group().count(), equalTo(before.withAdded(group).size()));
        //Groups after = app.db().groups();
        //Doesn't work MatcherAssert.assertThat(after, equalTo(before.withAdded(group)));
        verifyGroupListInUI();
/*
        GroupData newGroup = new GroupData()
                .withId(app.group().all().stream().mapToInt((g) -> g.getId()).max().getAsInt())
                .withName(group.getName())
                .withHeader(group.getHeader())
                .withFooter(group.getFooter());
        MatcherAssert.assertThat(after, equalTo(newGroup));
*/

    }

    @Test//(enabled = false)
    public void testBadGroupCreation() {
        //app.goTo().groupPage();
        Groups before = app.db().groups(); //app.group().all();
        GroupData group = new GroupData().withName("test2'");
        app.group().create(group);

        MatcherAssert.assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.db().groups();
        MatcherAssert.assertThat(after, equalTo(before));
    }
}