package com.sda.iManu.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

/**
 * Created by teos on 2016-10-05.
 */
@Getter
@Setter
@ToString
public class Cart {
    @Id
    private String id;

    private String paidDate;

    private String userId;

    private String travelId;

    private String travelName;

    private String travelDate;

    private Integer seats;

    private BigDecimal price;

    private Boolean payment;

    public Cart(String id,String paidDate,String userId,String travelId, String travelName, String travelDate, Integer seats,BigDecimal price,Boolean payment) {

        this.id = id;
        this.paidDate = paidDate;
        this.userId = userId;
        this.travelId = travelId;
        this.travelName = travelName;
        this.travelDate = travelDate;
        this.seats = seats;
        this.price = price;
        this.payment = payment;
    }
}
