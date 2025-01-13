package com.mercant.real.estate.municipality.utils;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class StringUtil {
    private StringUtil() {
    }

    public static String getEntityNextToFrom(String query) {
        final String regex = "(?<=\\bFROM\\b\\s).*";
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(query)
                .results().findFirst().map(MatchResult::group)
                .map(string -> string.replace(" ", ""))
                .orElse("");
    }
}
