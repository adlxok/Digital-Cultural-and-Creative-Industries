package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.pojo.CartItem;
import org.example.digitalculturalportal.pojo.Products;
import org.example.digitalculturalportal.service.CartService;
import org.example.digitalculturalportal.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/12 20:47
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductsService productsService;

    @Override
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> list = new ArrayList<>();
        String cartKey = "cart:user:" + userId;
        Map<Object, Object> map = redisTemplate.opsForHash().entries(cartKey);
        map.forEach((key, value) -> {

            int productId = Integer.parseInt((String) key);
            int quantity = Integer.parseInt(value.toString());
            Products products = productsService.selectProductById(productId);
            CartItem cartItem = new CartItem();
            cartItem.setId(productId);
            cartItem.setName(products.getName());
            cartItem.setPrice(products.getPrice());
            cartItem.setImage(products.getImageUrl());
            cartItem.setQuantity(quantity);
            cartItem.setSelected(false);
            list.add(cartItem);
        });

        return list;
    }


}
