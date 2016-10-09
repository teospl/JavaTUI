package com.sda.iManu.dto;

import com.sda.iManu.domain.Rating;
import com.sda.iManu.domain.User;
import com.sda.iManu.service.impl.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

/**
 * Created by teos on 2016-10-01.
 */
@Getter
@Setter
@ToString
public class RatingDto {

    @Id
    private String id;

    private String userId;

    private String travelId;

    private String rating;

    private String comment;

    public static RatingDto fromRatings(final Rating rating) {
        final RatingDto result = new RatingDto();
        result.setId(rating.getId());
        result.setUserId(rating.getUserId());
        result.setTravelId(rating.getTravelId());
        result.setRating(rating.getRating());
        result.setComment(rating.getComment());
        return result;
    }
}
