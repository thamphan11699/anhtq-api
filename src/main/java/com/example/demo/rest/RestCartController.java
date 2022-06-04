package com.example.demo.rest;

import com.example.demo.Constants;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartProductDto;
import com.example.demo.dto.CategoryDto;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/api/cart")
public class RestCartController {

    @Autowired
    CartService cartService;

    @GetMapping("/{userId}")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<CartDto> getCartByUser(@PathVariable Long userId) {
        CartDto result = cartService.getByUserId(userId);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-to-cart")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto) {
        CartDto result = cartService.addToCart(cartDto, cartDto.getCartProduct());
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete-to-cart/{cartId}")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<CartDto> deleteItemInCart(@RequestBody CartProductDto cartProductDto, @PathVariable Long cartId) {
        CartDto result = cartService.deleteCartItem(cartProductDto, cartId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update-to-cart")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<CartDto> updateAmount(@RequestBody CartDto cartDto) {
        CartDto result = cartService.updateCart(cartDto, cartDto.getCartProduct(), cartDto.getCartProduct().getAmount());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add-amount/{cartId}")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<CartDto> addAmount(@RequestBody CartProductDto cartProductDto, @PathVariable Long cartId) {
        CartDto result = cartService.updateCartAmount(cartProductDto, 1L, cartId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/remove-amount/{cartId}")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<CartDto> removeAmount(@RequestBody CartProductDto cartProductDto, @PathVariable Long cartId) {
        CartDto result = cartService.updateCartAmount(cartProductDto, -1L, cartId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/typing-amount/{cartId}")
    @Secured({Constants.ROLE_USER})
    public ResponseEntity<CartDto> typingAmount(@RequestBody CartProductDto cartProductDto, @PathVariable Long cartId) {
        CartDto result = cartService.typingAmount(cartProductDto, cartProductDto.getAmount() != null ? cartProductDto.getAmount() : 0, cartId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
