package com.sda.iManu.converter;

import com.sda.iManu.domain.Cart;
import com.sda.iManu.dto.CartDto;

/**
 * Created by teos on 2016-10-05.
 */
public class CartDtoToCartConverter  implements IConverter<CartDto, Cart> {

    public Cart convert(CartDto cartDto) {
        final Cart result = new Cart(
                cartDto.getId(),
                cartDto.getPaidDate(),
                cartDto.getUserId(),
                cartDto.getTravelId(),
                cartDto.getTravelName(),
                cartDto.getTravelDate(),
                cartDto.getSeats(),
                cartDto.getPrice(),
                cartDto.getPayment());
        return result;
    }
}
