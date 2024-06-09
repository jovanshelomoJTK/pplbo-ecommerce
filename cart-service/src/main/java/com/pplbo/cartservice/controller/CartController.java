package com.pplbo.cartservice.controller;

import com.pplbo.cartservice.dto.CartDTO;
import com.pplbo.cartservice.service.CartService;
import com.pplbo.cartservice.jwt.customannotations.AllowedRoles;
import com.pplbo.cartservice.jwt.customannotations.UserDataFromToken;
import com.pplbo.cartservice.jwt.model.JwtUserData;
import com.pplbo.cartservice.jwt.model.JwtUserData.Role;
import com.pplbo.cartservice.model.Cart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/")
    @AllowedRoles({ Role.CUSTOMER })
    public List<Cart> getCart(@UserDataFromToken JwtUserData userData) {
        return cartService.getCart(userData.getId());
    }

    @PostMapping("/add")
    @AllowedRoles({ Role.CUSTOMER })
    public Cart addItemToCart(@RequestBody CartDTO cart, @UserDataFromToken JwtUserData userData) {
        return cartService.addItemToCart(cart, userData.getId());
    }

    @DeleteMapping("/remove/{productId}")
    @AllowedRoles({ Role.CUSTOMER })
    public void removeItemFromCart(@PathVariable String productId, @UserDataFromToken JwtUserData userData) {
        cartService.removeItemFromCart(userData.getId(), productId);
    }

    @PutMapping("/updateQuantity/{productId}")
    @AllowedRoles({ Role.CUSTOMER })
    public Cart updateQuantityItemInCart(@PathVariable String productId, @RequestParam int quantity, @UserDataFromToken JwtUserData userData) {
        return cartService.updateQuantityItemInCart(userData.getId(), productId, quantity);
    }

    @GetMapping("/total")
    @AllowedRoles({ Role.CUSTOMER })
    public Double getTotal(@UserDataFromToken JwtUserData userData) {
        return cartService.getTotal(userData.getId());
    }
}
