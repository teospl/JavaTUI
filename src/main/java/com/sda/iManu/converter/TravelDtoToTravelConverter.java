package com.sda.iManu.converter;

import com.sda.iManu.domain.Travel;
import com.sda.iManu.dto.TravelDto;

/**
 * Created by teos on 2016-09-29.
 */
public class TravelDtoToTravelConverter implements IConverter<TravelDto,Travel>{

        public Travel convert(TravelDto travelDto) {
            final Travel result = new Travel(
                    travelDto.getId(),
                    travelDto.getHotel(),
                    travelDto.getCountry(),
                    travelDto.getCurort(),
                    travelDto.getDescription(),
                    travelDto.getPrice(),
                    travelDto.getAccess(),
                    travelDto.getStartDate(),
                    travelDto.getStay(),
                    travelDto.getFood(),
                    travelDto.getRating(),
                    travelDto.getPhotoUrl(),
                    travelDto.getMainRate());
            return result;
        }
}
