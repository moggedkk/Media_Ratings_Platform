package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class Rating {
    @Getter @Setter
    int media_id;
    @Getter @Setter
    int star_value;
    @Getter @Setter
    List<User> liked_by;
    @Getter @Setter
    Date release_type;
}
