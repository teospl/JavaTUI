package com.sda.iManu.dto;

import com.sda.iManu.domain.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * Created by teos on 2016-09-29.
 */
@Getter
@Setter
@ToString
public class TravelDto {

    @Id
    private String id;

    private String hotel;

    private String country;

    private String curort;

    private String description;

    private BigDecimal price;

    private Access access;

    //@DateTimeFormat(pattern = "YYYY-MMM-dd")
    private String startDate;

    private Stay stay;

    private Food food;

    private LinkedList<Integer> rating;

    private String photoUrl;

    private Double mainRate = 0.0;

    private String travelName;

    public static TravelDto fromTravel(final Travel travel) {
        final TravelDto result = new TravelDto();

        result.setId(travel.getId());
        result.setHotel(travel.getHotel());
        result.setCountry(travel.getCountry());
        result.setCurort(travel.getCurort());
        result.setDescription(travel.getDescription());
        result.setPrice(travel.getPrice());
        result.setAccess(travel.getAccess());
        result.setStartDate(travel.getStartDate());
        result.setStay(travel.getStay());
        result.setFood(travel.getFood());
        result.setRating(travel.getRating());
        result.setPhotoUrl(travel.getPhotoUrl());
        result.setMainRate(travel.getMainRate());
        result.setTravelName(travel.getHotel() + " - " + travel.getCurort() + " - " + travel.getStartDate());
        return result;
    }
}
