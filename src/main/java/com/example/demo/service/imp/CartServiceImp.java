package com.example.demo.service.imp;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartProductDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.model.Cart;
import com.example.demo.model.CartProduct;
import com.example.demo.repository.CartProductRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImp implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<CartDto> getAllOrder(SearchDto dto) {
        return null;
    }

    @Override
    public CartDto getById(Long id) {
        return null;
    }

    @Override
    public CartDto getByUserId(Long id) {
        try {
            if (id != null) {
                Cart cart = null;
                //Check exit
                Long exit = cartRepository.checkCartExit(id);
                if (exit > 0) {
                    cart = cartRepository.getCartByUser(id);
                } else {
                    cart = new Cart();
                    cart.setUser(userRepository.getById(id));
                    cartRepository.save(cart);
                }
                return new CartDto(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartDto updateCart(CartDto cartDto, CartProductDto cartProductDto, Long amount) {
        try {
            if (cartDto.getId() != null && cartProductDto.getId() != null) {
                CartProduct cartProduct = cartProductRepository.getById(cartProductDto.getId());
                cartProduct.setAmount(cartProductDto.getAmount() + amount);
                cartProductRepository.save(cartProduct);
                return new CartDto(cartRepository.getById(cartDto.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartDto addToCart(CartDto cartDto, CartProductDto cartProductDto) {
        try {
            Cart cart = null;
            if (cartDto.getId() == null) {
                // Khach hang chua them gio hang lan nao, tao moi
                cart = new Cart();
            } else {
                cart = cartRepository.getById(cartDto.getId());
            }
            CartProduct cartProduct = null;
            if (cartProductDto.getId() != null) {
                cartProduct = cartProductRepository.getById(cartProductDto.getId());
            } else {
                cartProduct = new CartProduct();
            }

            if (cartProductDto.getProduct() != null && cartProductDto.getProduct().getId() != null) {
                cartProduct.setProduct(productRepository.getById(cartProductDto.getProduct().getId()));
            }
            cartProduct.setAmount(cartProductDto.getAmount());
            cartProduct.setIsDelete(1L);
            cart = cartRepository.save(cart);
            cartProduct.setCart(cart);
            cartProductRepository.save(cartProduct);
            return new CartDto(cart);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartDto deleteCartItem(CartProductDto cartProductDto, Long id) {
        try {
            CartProduct cartProduct = cartProductRepository.getById(cartProductDto.getId());
            cartProduct.setIsDelete(2L);
            return new CartDto(cartRepository.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartDto updateCartAmount(CartProductDto cartProductDto, Long bonus, Long cartId) {
        try {
            CartProduct cartProduct = cartProductRepository.getById(cartProductDto.getId());
            cartProduct.setAmount(cartProduct.getAmount() + bonus);
            cartProductRepository.save(cartProduct);
            return new CartDto(cartRepository.getById(cartId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
