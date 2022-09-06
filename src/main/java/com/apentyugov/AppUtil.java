package com.apentyugov;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {
    public static final String INPUT_PATTERN = "^([0-9]+([.][0-9]*)?|[.][0-9]+)+\\s[a-zA-Z]+\\s=\\s([0-9]+([.][0-9]*)?|[.][0-9]+)+\\s[a-zA-Z]+$";
    public static final String QUESTION_PATTERN = "^([0-9]+([.][0-9]*)?|[.][0-9]+)+\\s[a-zA-Z]+\\s=\\s(\\?)+\\s[a-zA-Z]+$";

    public static boolean isStringMatches(String inputString, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public static String[] parseString(String input) {
        return input
                .replace("= ", "")
                .split(" ");
    }

}
