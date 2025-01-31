package com.huuquy.service;

import com.huuquy.model.Cart;
import com.huuquy.model.CartItem;
import com.huuquy.request.AddCartItemRequest;

public interface CartService {
    CartItem addItemToCart(AddCartItemRequest request, String jwt) throws  Exception;

    CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws  Exception;

    Cart removeItemFromCart(Long cartItem, String jwt) throws  Exception;

    Long calculateCartTotals(Cart cart) throws  Exception;

    Cart findCartById(Long id) throws Exception;

    Cart findCartByUserId(Long userId) throws  Exception;

    Cart clearCart(Long userId) throws  Exception;
}
