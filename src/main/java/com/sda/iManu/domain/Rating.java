package com.sda.iManu.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * Created by teos on 2016-10-01.
 */
@Getter
@Setter
@ToString
public class Rating {
    @Id
    private String id;

    private String userId;

    private String travelId;

    private String rating;

    private String comment;

    public Rating(String id, String userId, String travelId, String rating, String comment) {
        this.id = id;
        this.userId = userId;
        this.travelId = travelId;
        this.rating = rating;
        this.comment = comment;
    }

}
