package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.CartItem;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/12 20:47
 **/
public interface CartService {

    List<CartItem> getCartItems(int userId);

}
