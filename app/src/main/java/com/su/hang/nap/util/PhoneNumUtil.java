package com.su.hang.nap.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

import com.su.hang.nap.bean.PhoneNum;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumUtil {
    private static PhoneNumUtil instance = null;

    private PhoneNumUtil() {

    }

    public static PhoneNumUtil getInstance() {
        if (instance == null) {
            instance = new PhoneNumUtil();
        }
        return instance;
    }

    public List<PhoneNum> getPhoneNum(Activity context) {
        List<PhoneNum> list = new ArrayList<PhoneNum>();
        try {
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    PhoneNum entity = new PhoneNum();

                    int nameFieldColumnIndex = cursor
                            .getColumnIndex(PhoneLookup.DISPLAY_NAME);

                    String contact = cursor.getString(nameFieldColumnIndex);
                    contact = contact.replaceAll(" ", "");
                    contact = contact.trim();
                    entity.name = contact;
                    String ContactId = cursor.getString(cursor
                            .getColumnIndex(ContactsContract.Contacts._ID));

                    Cursor phone = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + ContactId, null, null);

                    if (phone != null) {
                        while (phone.moveToNext()) {

                            String PhoneNumber = phone
                                    .getString(phone
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            PhoneNumber = PhoneNumber.replaceAll(" ", "");
                            PhoneNumber = PhoneNumber.trim();
                            entity.number = PhoneNumber;
                        }
                        list.add(entity);
                    }

                }
            }

            return list;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

}
