package com.sda.iManu.web.controller;

import com.sda.iManu.converter.CartDtoToCartConverter;
import com.sda.iManu.domain.Cart;
import com.sda.iManu.domain.Travel;
import com.sda.iManu.domain.User;
import com.sda.iManu.dto.CartDto;
import com.sda.iManu.service.SecUserDetails;
import com.sda.iManu.service.impl.CartService;
import com.sda.iManu.service.impl.TravelService;
import com.sda.iManu.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by teos on 2016-10-05.
 */
@Controller
public class CartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);


    private CartDtoToCartConverter converter = new CartDtoToCartConverter();

    @Autowired
    CartService cartService;

    @Autowired
    TravelService travelService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/cart/products", method = RequestMethod.GET)
    public ModelAndView getProducts(@ModelAttribute CartDto cartDto) {
        LOGGER.info("Wywołanie messages");
        String userId = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getId();
        User user = userService.getUserById(userId);
        String buyer = user.getFirstName() + " " + user.getLastName();
        List<CartDto> products = cartService
                .getCartByUserId(userId)
                .stream()
                .map(CartDto::fromCart)
                .collect(Collectors.toCollection(LinkedList::new));
        LOGGER.info("Products by userid list: {}", products);

        return new ModelAndView("cart").addObject("productsList", products).addObject("buyer", buyer).addObject("data", new CartDto()); //.addObject("data", new CartDto());

    }

    @RequestMapping(value = "/cart/new", method = RequestMethod.GET)
    public String startCartProcess(@RequestParam String travelId, ModelMap model) {
        LOGGER.info("Wywolanie GET na cart/new");
        ModelAndView modelAndView = new ModelAndView();
        CartDto cartDto = new CartDto();
        Travel travel = travelService.getTravelById(travelId);
        String userId = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getId();

        cartDto.setUserId(userId);
        cartDto.setTravelId(travelId);
        cartDto.setTravelName(travel.getHotel() +" / "+ travel.getCountry() + " / " + travel.getCurort());
        cartDto.setTravelDate(travel.getStartDate());
        cartDto.setPrice(travel.getPrice());
        cartDto.setSeats(1);
        cartDto.setPayment(false);

            if (cartService.saveCart(converter.convert(cartDto))) {
                LOGGER.info("New Cart created: {}", cartDto);
                return "redirect:/cart/products";
            } else {
                LOGGER.info("cannot send Message");
                return "redirect:/travel/getTravel?travelId=" + cartDto.getTravelId();
            }

    }

    @RequestMapping(value = "/cart/pay", method = RequestMethod.POST)
    public String buy(
            @ModelAttribute(value = "data") @Valid CartDto cartDto, @RequestParam String cartId,
            BindingResult result,
            ModelMap model) {

        LOGGER.info("Wywołanie post na cart/buy, przekazany obiekt: {}", cartId);
            Cart currentCart = cartService.getCartById(cartId);
//        LOGGER.info("Wywołanie post na cart/buy, przekazany obiekt: {}", currentCart);
//            currentCart.setSeats(cartDto.getSeats());
//              currentCart.setPrice(cartDto.getPrice());
//                currentCart.setPayment(true);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {
            cartDto.setId(cartId);

            cartDto.setPaidDate(dateFormat.format(cal.getTime()));

            cartDto.setUserId(currentCart.getUserId());

            cartDto.setTravelId(currentCart.getTravelId());

            cartDto.setTravelName(currentCart.getTravelName());

            cartDto.setTravelDate(currentCart.getTravelDate());

            cartDto.setPayment(true);
            if (cartService.saveCart(converter.convert(cartDto))) {
                LOGGER.info("Cart paid: {}", cartDto);
                return "redirect:/cart/products";
            } else {
                LOGGER.info("cannot add payment");
                return "redirect:/cart/products";
            }
        }
    }

}
