package org.example.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class User {
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @Getter @Setter
    String username;
    @Getter @Setter
    String password;
}
