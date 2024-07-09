package com.WinUIAutomation.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

public class WinAppDataGenerateUtils {

    private WinAppDataGenerateUtils() {
        super();
    }

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom random = new SecureRandom();

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(AB.charAt(random.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String randomStringHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); // Hexadecimal encoding
    }

    public static String randomStringUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static int randomNumberIntFromTo(int from, int to) {
        int random_int = (int) Math.floor(Math.random() * (to - from + 1) + from);
        return random_int;
    }

    public static String randomFullName() {
        return WinAppDataFakerUtils.getFaker().name().fullName();
    }

    public static String randomPhoneNumber() {
        return WinAppDataFakerUtils.getFaker().phoneNumber().phoneNumber();
    }

    public static String randomCountry() {
        return WinAppDataFakerUtils.getFaker().address().country();
    }

    public static String randomZipCode() {
        return WinAppDataFakerUtils.getFaker().address().zipCode();
    }

    public static String randomAddress() {
        return WinAppDataFakerUtils.getFaker().address().fullAddress();
    }

    public static String randomCity() {
        return WinAppDataFakerUtils.getFaker().address().cityName();
    }

    public static String randomState() {
        return WinAppDataFakerUtils.getFaker().address().state();
    }

    public static String randomStreetName() {
        return WinAppDataFakerUtils.getFaker().address().streetName();
    }

    public static String randomJobTitle() {
        return WinAppDataFakerUtils.getFaker().job().title();
    }

    public static String randomJobSkills() {
        return WinAppDataFakerUtils.getFaker().job().keySkills();
    }

    public static String randomProgrammingLanguage() {
        return WinAppDataFakerUtils.getFaker().programmingLanguage().name();
    }

}
