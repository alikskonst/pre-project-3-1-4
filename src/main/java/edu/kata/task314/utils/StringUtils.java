package edu.kata.task314.utils;

public class StringUtils {

    public static boolean isEmpty(String line) {
        return line == null || line.length() == 0;
    }

    public static boolean isNotEmpty(String line) {
        return !isEmpty(line);
    }
}
