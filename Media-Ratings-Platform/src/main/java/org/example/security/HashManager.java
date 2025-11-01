package org.example.security;

import com.password4j.Password;

public class HashManager {
    public String hashPassword(String password) {
        return Password.hash(password)
                .addRandomSalt()
                .withArgon2()
                .getResult();
    }
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return Password.check(plainPassword, hashedPassword)
                .withArgon2();
    }
}
