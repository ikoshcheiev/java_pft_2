package ru.stqa.pft.sandbox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
    public static void main(String[] args) {
        String[] langs = {"Java", "C#", "Python", "PHP"};
        for(int i = 0; i < langs.length; i++){
            System.out.println("Я хочу выучить " + langs[i]);
        }

        for(String l : langs){
            System.out.println("Я хочу выучить " + l);
        }

        System.out.println("-------- Array List --------");
        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        languages.add("C#");
        languages.add("Python");
        languages.add("PHP");
        for(String lang : languages){
            System.out.println("Я хочу выучить " + lang);
        }

        System.out.println("----- Arrays.asList -----");
        languages = Arrays.asList("Java", "C#", "Python", "PHP");
        for(String lang : languages){ //for(int i = 0; i < languages.size(); i++)
            System.out.println("Я хочу выучить " + lang);
        }
    }
}
