package com.huuquy.controller;

import com.huuquy.model.Cart;
import com.huuquy.model.CartItem;
import com.huuquy.model.User;
import com.huuquy.request.AddCartItemRequest;
import com.huuquy.request.UpdateCartItemRequest;
import com.huuquy.service.CartService;
import com.huuquy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
    @PostMapping("/add")

    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request,

                                                  @RequestHeader("Authorization") String jwt) throws  Exception {
        CartItem cartItem =cartService.addItemToCart(request, jwt);
        return  new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    @PutMapping("/update")

    public ResponseEntity<CartItem> updateItemToCart(@RequestBody UpdateCartItemRequest request,

                                                     @RequestHeader("Authorization") String jwt) throws  Exception {
        CartItem cartItem =cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return  new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")

    public ResponseEntity<Cart> deleteCartItem(@PathVariable Long id,

                                                 @RequestHeader("Authorization") String jwt) throws  Exception {
        Cart cart = cartService.removeItemFromCart(id,jwt);
        return  new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/clear")

    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws  Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartService.clearCart(user.getId());
        return  new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/user")

    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws  Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartService.findCartByUserId(user.getId());
        return  new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
