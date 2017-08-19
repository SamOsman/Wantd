package com.example.samosman.wantd_final;

/**
 * Created by Sam Osman on 2017-07-31.
 */

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Result  extends AppCompatActivity  {

    static ArrayList<String> contacts = new ArrayList<>();
    static ArrayList<Person> positiveMatch = new ArrayList<>();
    static TextView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        contactList = (TextView)findViewById(R.id.contactList);
        Log.i("!!!", "Creating contact list");
        initContactList();
        Log.i("!!!", "Created contact list");
    }

    protected void initContactList(){
        //get list of contacts.....
        //gives access to the phones content layer
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            }
        }
        cur.close();
    }

    public static void compareNames() {
        String firstContact;
        String lastContact;
        String firstCriminal;
        String lastCriminal;
        String temporary;
        for (String contact : contacts) {
            contact = contact.trim();
            if (contact.indexOf(' ') == -1) { // only a first name
                firstContact = contact;
                lastContact = null;
            } else {
                firstContact = contact.substring(0, contact.indexOf(' '));
                lastContact = contact.substring(contact.lastIndexOf(' ') + 1);
            }
            for (Person criminal : Person.people) {
                temporary = criminal.toString();
                firstCriminal = temporary.substring(0, temporary.indexOf(' '));
                if (firstContact.equalsIgnoreCase(firstCriminal)) {
                    if (lastContact == null) {
                        positiveMatch.add(criminal);
                    }
                    else {
                        lastCriminal = temporary.substring(temporary.lastIndexOf(' ') + 1);
                        if (lastContact.equalsIgnoreCase(lastCriminal)) {
                            positiveMatch.add(criminal);
                        }
                    }
                }
            }
        }
    }

    protected static void displayFoundCriminals(){
        Log.i("!!!", "Displaying");
        if (positiveMatch.isEmpty()){
            contactList.setText("Congratulations :)  You don't know any criminals!");
        } else {
            String nameList = "You know these criminals:\n";
            for (Person person : positiveMatch){
                nameList += "* " + person.toString() + "\n";
            }
            contactList.setText(nameList);
        }
    }

}
