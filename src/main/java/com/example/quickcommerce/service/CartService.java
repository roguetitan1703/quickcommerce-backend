package com.example.quickcommerce.service;

import com.example.quickcommerce.model.CartItem;
import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.model.User;
import com.example.quickcommerce.repository.CartItemRepository;
import com.example.quickcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    // Get cart items for a user
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    // Add or update a cart item
    public CartItem addToCart(User user, Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Optional<CartItem> existingItemOpt = cartItemRepository.findByUserAndProductProductId(user, productId);

        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            // Update existing item quantity
            cartItem = existingItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Create new cart item
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(productOpt.get());
            cartItem.setQuantity(quantity);
        }

        return cartItemRepository.save(cartItem);
    }

    // Update cart item quantity
    public CartItem updateCartItemQuantity(Long itemId, Integer quantity) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(itemId);

        if (cartItemOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart item not found");
        }

        CartItem cartItem = cartItemOpt.get();
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    // Remove item from cart
    public void removeFromCart(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    // Clear the user's cart (useful after order placement)
    public void clearCart(User user) {
        List<CartItem> userItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(userItems);
    }
}