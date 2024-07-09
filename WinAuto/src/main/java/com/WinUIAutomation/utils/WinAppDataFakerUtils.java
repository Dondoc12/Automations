package com.WinUIAutomation.utils;

import com.WinUIAutomation.constants.WinAppConstants;
import net.datafaker.Faker;

import java.util.Locale;

public class WinAppDataFakerUtils {
    //Java Locale List: https://www.viralpatel.net/java-locale-list-tutorial/
    private static Faker faker = null; //English US

    public static Faker getFaker() {
        if (faker == null) {
            faker = new Faker(new Locale(WinAppConstants.LOCATE));
        }
        return faker;
    }

    public static void setFaker(Faker faker) {
        WinAppDataFakerUtils.faker = faker;
    }

    public static void setLocate(String LocateName) {
        faker = new Faker(new Locale(LocateName));
    }
}

