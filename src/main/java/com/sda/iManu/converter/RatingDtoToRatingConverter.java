package com.sda.iManu.converter;

import com.sda.iManu.domain.Rating;
import com.sda.iManu.dto.RatingDto;

/**
 * Created by teos on 2016-10-01.
 */
public class RatingDtoToRatingConverter
        implements IConverter<RatingDto, Rating> {

    public Rating convert(RatingDto ratingDto) {
        final Rating result = new Rating(
                ratingDto.getId(),
                ratingDto.getUserId(),
                ratingDto.getTravelId(),
                ratingDto.getRating(),
                ratingDto.getComment());
        return result;
    }
}
