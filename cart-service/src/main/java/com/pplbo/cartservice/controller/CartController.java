package com.pplbo.cartservice.controller;

import com.pplbo.cartservice.dto.CartDTO;
import com.pplbo.cartservice.service.CartService;
import com.pplbo.cartservice.jwt.customannotations.AllowedRoles;
import com.pplbo.cartservice.jwt.customannotations.UserDataFromToken;
import com.pplbo.cartservice.jwt.model.JwtUserData;
import com.pplbo.cartservice.jwt.model.JwtUserData.Role;
import com.pplbo.cartservice.model.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/")
    @AllowedRoles({ Role.CUSTOMER })
    public ResponseEntity<List<Cart>> getCart(@UserDataFromToken JwtUserData userData) {
        try {
            List<Cart> cart = cartService.getCart(userData.getId());
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    @AllowedRoles({ Role.CUSTOMER })
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartDTO cart, @UserDataFromToken JwtUserData userData) {
        try {
            Cart newCart = cartService.addItemToCart(cart, userData.getId());
            return new ResponseEntity<>(newCart, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove/{productId}")
    @AllowedRoles({ Role.CUSTOMER })
    public ResponseEntity<Void> removeItemFromCart(@PathVariable String productId,
            @UserDataFromToken JwtUserData userData) {
        try {
            cartService.removeItemFromCart(userData.getId(), productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/remove")
    @AllowedRoles({ Role.CUSTOMER })
    public ResponseEntity<Void> removeAllItemsFromCart(@UserDataFromToken JwtUserData userData) {
        try {
            cartService.removeAllItemsFromCart(userData.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateQuantity/{productId}")
    @AllowedRoles({ Role.CUSTOMER })
    public ResponseEntity<Cart> updateQuantityItemInCart(@PathVariable String productId, @RequestParam int quantity, @UserDataFromToken JwtUserData userData) {
        try {
            Cart updatedCart = cartService.updateQuantityItemInCart(userData.getId(), productId, quantity);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total")
    @AllowedRoles({ Role.CUSTOMER })
    public ResponseEntity<Double> getTotal(@UserDataFromToken JwtUserData userData) {
        try {
            Double total = cartService.getTotal(userData.getId());
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
