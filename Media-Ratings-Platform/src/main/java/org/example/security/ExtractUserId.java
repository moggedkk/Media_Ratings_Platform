package org.example.security;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public interface ExtractUserId {
    static String ExtractId(String path) {
        Pattern pattern = Pattern.compile("/users/([0-9]+)/profile");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
