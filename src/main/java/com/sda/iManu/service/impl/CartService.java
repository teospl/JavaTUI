package com.sda.iManu.service.impl;

import com.sda.iManu.domain.Cart;
import com.sda.iManu.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by teos on 2016-10-05.
 */
@Component
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCartByUserId(String id){
        List<Cart> products = cartRepository.findAllByUserId(id);
        return products;
    }
    public Cart getCartById(String id){
        Cart cart = cartRepository.findById(id);
        return cart;
    }

    public boolean saveCart(Cart cart) {
        cartRepository.save(cart);
        return true;
    }
}
