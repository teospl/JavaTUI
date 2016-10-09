package com.sda.iManu.domain;

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
public class Travel {

    @Id
    private String id;

    private String hotel;

    private String country;

    private String curort;

    private String description;

    private BigDecimal price;

    private Access access;

    private String startDate;

    private Stay stay;

    private Food food;

    private LinkedList<Integer> rating;

    private String photoUrl;

    private Double mainRate;

    public Travel(String id, String hotel, String country, String curort, String description, BigDecimal price, Access access, String startDate, Stay stay, Food food, LinkedList<Integer> rating, String photoUrl, Double mainRate) {
        this.id = id;
        this.hotel = hotel;
        this.country = country;
        this.curort = curort;
        this.description = description;
        this.price = price;
        this.access = access;
        this.startDate = startDate;
        this.stay = stay;
        this.food = food;
        this.rating = rating;
        this.photoUrl = photoUrl;
        this.mainRate = mainRate;
    }
}
