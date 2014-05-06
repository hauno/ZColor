package me.hauno.bukkit.zcolor.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUtil {

    public static Set<String> getUniqueMatches(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        HashSet<String> uniqueMatches = new HashSet<String>();

        while (matcher.find()) {
            uniqueMatches.add(matcher.group());
        }

        return uniqueMatches;
    }

}
