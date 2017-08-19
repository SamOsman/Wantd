package com.example.samosman.wantd_final;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by James Burgess on 2017-07-31.
 */

class Person {

    public static ArrayList<Person> people = new ArrayList<>();
    public String urlSuffix;

    public Person(String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }

    @Override
    public String toString() {
        String[] names = this.urlSuffix.substring(7).split("-");
        String string = Character.toUpperCase(names[0].charAt(0)) + names[0].substring(1);
        for (int i = 1; i < names.length; i++) {
            string += " " + Character.toUpperCase(names[i].charAt(0)) + names[i].substring(1);
        }
        return string;
    }

    public static void parse(String string) {
        if (string.startsWith("!!!")) {
            Log.e("!!!", string);
            return;
        }
        String URL;
        String beforeURL = "pddng-tp-md\"><a href=\"";
        int index = 0;
        while (true) {
            index = string.indexOf(beforeURL, index);
            if (index == -1) {
                return;
            }
            index += beforeURL.length();
            URL = string.substring(index, string.indexOf('"', index));
            if (URL.equals("wanted/murder-suspect") || URL.equals("wanted/sexual-assault-suspect")) {
                continue; // these aren't names!
            }
            people.add(new Person(URL));
        }
    }

}
