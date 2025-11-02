package org.example.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ExtractMediaId {
    static String ExtractId(String path) {
        Pattern pattern = Pattern.compile("/media/([0-9]+)");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
