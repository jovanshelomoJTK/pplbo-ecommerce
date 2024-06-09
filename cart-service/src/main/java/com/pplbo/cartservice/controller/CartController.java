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

    @DeleteMapping("/remove/{id}")
    public void removeItemFromCart(@PathVariable Long id) {
        cartService.removeItemFromCart(id);
    }

    @PutMapping("/update")
    public Cart updateItemInCart(@RequestBody CartDTO cart,@UserDataFromToken JwtUserData userData ) {
        return cartService.updateItemInCart(cart, userData.getId());
    }

    // @PatchMapping("/update/{id}")
    // public CartDTO partiallyUpdateItemInCart(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
    //     return cartService.partiallyUpdateItemInCart(id, updates);
    // }

    @GetMapping("/total")
    public Double getTotal() {
        return cartService.getTotal();
    }
}
