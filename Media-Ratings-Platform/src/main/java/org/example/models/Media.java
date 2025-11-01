package org.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class Media {
    public Media(String user_id, String title, String description, Date releaseYear, int ageRestriction, mediaType mediaType, List<genre> genres) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.ageRestriction = ageRestriction;
        this.mediaType = mediaType;
        this.genres = genres;
    }
    @Getter @Setter
    String user_id;
    @Getter @Setter
    String title;
    @Getter @Setter
    String description;
    @Getter @Setter
    Date releaseYear;
    @Getter @Setter
    int ageRestriction;
    @Getter @Setter
    mediaType mediaType;
    @Getter @Setter
    List<genre> genres;
}
