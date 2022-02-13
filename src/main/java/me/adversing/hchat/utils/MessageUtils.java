package me.adversing.hchat.utils;

import java.util.Arrays;

/**
 * This class is used to check if a word contains any element ({@link String}) from an {@link java.util.ArrayList}
 */
public class MessageUtils {
    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }
}
