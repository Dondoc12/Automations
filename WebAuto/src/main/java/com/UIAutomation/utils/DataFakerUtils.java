package com.UIAutomation.utils;

import com.UIAutomation.constants.Constants;
import net.datafaker.Faker;

import java.util.Locale;

public class DataFakerUtils {
    //Java Locale List: https://www.viralpatel.net/java-locale-list-tutorial/
    private static Faker faker = null; //English US

    public static Faker getFaker() {
        if (faker == null) {
            faker = new Faker(new Locale(Constants.LOCATE));
        }
        return faker;
    }

    public static void setFaker(Faker faker) {
        DataFakerUtils.faker = faker;
    }

    public static void setLocate(String LocateName) {
        faker = new Faker(new Locale(LocateName));
    }
}

