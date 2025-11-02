package org.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
public class Media {
    public Media(int user_id, String title, String description, int releaseYear, int ageRestriction, mediaType mediaType, List<Genre> genres) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.ageRestriction = ageRestriction;
        this.mediaType = mediaType;
        this.genres = genres;
    }
    @Getter @Setter
    int user_id;
    @Getter @Setter
    String title;
    @Getter @Setter
    String description;
    @Getter @Setter
    int releaseYear;
    @Getter @Setter
    int ageRestriction;
    @Getter @Setter
    mediaType mediaType;
    @Getter @Setter
    List<Genre> genres;
}
