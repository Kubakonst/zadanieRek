package com.britenet.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactTypeChecker {

    public ContactType getType(String contact) {
        if (isPhone(contact)) {
            return ContactType.PHONE;
        } else if (isEmail(contact)) {
            return ContactType.EMAIL;
        } else if (isJabber(contact)) {
            return ContactType.JABBER;
        }
        return ContactType.UNKNOWN;
    }

    private boolean isPhone(String contact) {
        Pattern p = Pattern.compile("^(\\+?[0-9][0-9])? ?-?[0-9][0-9][0-9] ?-?[0-9][0-9][0-9] ?-?[0-9][0-9][0-9]$");
        return p.matcher(contact).matches();
    }

    private boolean isEmail(String contact) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(contact);
        return m.matches();
    }

    private boolean isJabber(String contact) {
        String[] parameters = contact.split("@");
        if (parameters.length == 2) {
            return parameters[1].startsWith("xmpp") || parameters[1].startsWith("jabber");
        }
        return false;
    }
}
