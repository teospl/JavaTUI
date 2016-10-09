package com.sda.iManu.dto;

import com.sda.iManu.domain.Cart;
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
public class CartDto {
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

    public static CartDto fromCart(final Cart cart) {
        final CartDto result = new CartDto();
        result.setId(cart.getId());
        result.setPaidDate(cart.getPaidDate());
        result.setUserId(cart.getUserId());
        result.setTravelId(cart.getTravelId());
        result.setTravelName(cart.getTravelName());
        result.setTravelDate(cart.getTravelDate());
        result.setSeats(cart.getSeats());
        result.setPrice(cart.getPrice());
        result.setPayment(cart.getPayment());
        return result;
    }

}
