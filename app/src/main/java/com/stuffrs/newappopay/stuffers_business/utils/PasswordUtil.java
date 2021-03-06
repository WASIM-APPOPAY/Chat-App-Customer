package com.stuffrs.newappopay.stuffers_business.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public final class PasswordUtil {

    private static HashMap<Pattern, Double> patternsWeight;
    private static final double WEIGHT_BAD_PATTERN = .20;
    private static final double WEIGHT_COMMON_PATTERN = .40;

    private static HashMap<Pattern, Double> patternsQuality;
    private static final double QUALITY_POOR = 10;
    private static final double QUALITY_MEDIUM = 26;
    private static final double QUALITY_STRONG = 31;
    public static final String REGEX_PASSWORD_POLICY="Minimum password length is 8 characters"+"<br>" +
            "Required at least one "+ "<font color='#00baf2'>"+"uppercase letter"+ "</font>"+" from latin alphaber(A-Z)"+"<br>" +
            "Required at least one " +"<font color='#00baf2'>"+"lowercase letter"+ "</font>"+" from latin alphaber(a-z)"+"<br>" +
            "Required at least one "+ "<font color='#00baf2'>"+ "</font>"+" numberic"+"<br>" +
            "Required at least one "+ "<font color='#00baf2'>"+"non-alphanumeric"+ "</font>"+ " character(!@#$%^&*()_+=[]{}|')"+"<br>"+
            "<font color='#00baf2'>"+"Note+"+ "</font>"+" white space is note allowed";
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+='\\[\\]{}()])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");


    private PasswordUtil() {

        throw new AssertionError("This class isn't meant to be instantiated");
    }

    static {
        patternsWeight = new HashMap<>();
        patternsWeight.put(Pattern.compile("^\\d+$"), WEIGHT_BAD_PATTERN);                          // all digits
        patternsWeight.put(Pattern.compile("^[a-z]+\\d$"), WEIGHT_BAD_PATTERN);                     // all lower then 1 digit
        patternsWeight.put(Pattern.compile("^[A-Z]+\\d$"), WEIGHT_BAD_PATTERN);                     // all upper then 1 digit
        patternsWeight.put(Pattern.compile("^[a-zA-Z]+\\d$"), WEIGHT_COMMON_PATTERN);               // all alpha then 1 digit
        patternsWeight.put(Pattern.compile("^[a-z]+\\d+$"), WEIGHT_COMMON_PATTERN);                 // all lower then digits
        patternsWeight.put(Pattern.compile("^[a-z]+$"), WEIGHT_BAD_PATTERN);                        // all lower
        patternsWeight.put(Pattern.compile("^[A-Z]+$"), WEIGHT_BAD_PATTERN);                        // all upper
        patternsWeight.put(Pattern.compile("^[A-Z][a-z]+$"), WEIGHT_BAD_PATTERN);                   // only one upper at start
        patternsWeight.put(Pattern.compile("^[A-Z][a-z]+\\d$"), WEIGHT_BAD_PATTERN);                // only one upper at start followed by 1 digit
        patternsWeight.put(Pattern.compile("^[A-Z][a-z]+\\d+$"), WEIGHT_COMMON_PATTERN);            // only one upper at start followed by digits
        patternsWeight.put(Pattern.compile("^[a-z]+[._!\\- @*#]$"), WEIGHT_BAD_PATTERN);            // all lower followed by 1 special character
        patternsWeight.put(Pattern.compile("^[A-Z]+[._!\\- @*#]$"), WEIGHT_BAD_PATTERN);            // all upper followed by 1 special character
        patternsWeight.put(Pattern.compile("^[a-zA-Z]+[._!\\- @*#]$"), WEIGHT_COMMON_PATTERN);      // all alpha followed by 1 special character
        patternsWeight.put(Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"), WEIGHT_BAD_PATTERN);                                                 // email address
        patternsWeight.put(Pattern.compile("_^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?$_iuS"), WEIGHT_COMMON_PATTERN);                                    // web url

        patternsQuality = new HashMap<>();
        patternsQuality.put(Pattern.compile(".*\\d.*"), QUALITY_POOR);                              // contains at least one digit
        patternsQuality.put(Pattern.compile(".*[a-z].*"), QUALITY_MEDIUM);                          // contains at least one lowercase
        patternsQuality.put(Pattern.compile(".*[A-Z].*"), QUALITY_MEDIUM);                          // contains at least one uppercase
        patternsQuality.put(Pattern.compile(".*[^a-zA-Z0-9 ].*"), QUALITY_STRONG);                  // contains at least one special char
    }

    public static double getStrength(String pw) {
        //1. Get Quality
        double base = getBase(pw);
        //2. Get entropy
        double entropy = log2(Math.pow(base, pw.length()));
        //3. Average entropy with bad patternsWeight
        double quality = getQuality(pw);
        //4. Weigh unique symbol count
        double entropyWeighted = quality * entropy;//getEntropyWeightedByUniqueSymbolCount(quality, pw);

        return Math.min(entropyWeighted, 100.0);
    }

    private static double getBase(String pw) {
        double base = 1.0;

        Set<Map.Entry<Pattern, Double>> set = patternsQuality.entrySet();
        for (Map.Entry<Pattern, Double> item : set) {
            if (item.getKey().matcher(pw).matches())
                base += item.getValue();
        }

        return base;
    }

    private static double log2(double a) {
        return Math.log(a) / Math.log(2);
    }

    private static double getQuality(String pw) {
        Set<Map.Entry<Pattern, Double>> set = patternsWeight.entrySet();

        double weight = 1;
        for (Map.Entry<Pattern, Double> item : set) {

            if (item.getKey().matcher(pw).matches()) {
                weight = Math.min(weight, item.getValue());
            }
        }
        return weight;
    }

    public static boolean ddpw(String pw) {
        BigInteger int1 = BigInteger.valueOf(Integer.MAX_VALUE);
        byte[] bytes1 = int1.toByteArray();
        BigInteger int2 = BigInteger.valueOf((int) Math.pow(2, 16));
        byte[] bytes2 = int2.toByteArray();

        if (pw.matches("^\\d+$") && (pw.length() == bytes1.length * bytes2.length)) {
            SecureRandom random = new SecureRandom();
            int pos = random.nextInt(bytes1.length);
            return pw.charAt(pos) == pw.charAt(pos + bytes1.length) && pw.charAt(pos + bytes1.length) == pw.charAt(pos + (bytes1.length * 2));

        }
        return false;
    }

}