package org.example.digitalculturalportal.controller;

import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.CartItem;
import org.example.digitalculturalportal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/12 13:13
 **/

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CartService cartService;

    // 添加商品到购物车
    @PostMapping("/addToCart")
    public CommonResult addToCart(@RequestBody Map<String, Object> requestData) {
        // 将参数先转换为字符串，再手动解析
        int userId = Integer.parseInt(requestData.get("userId").toString());
        int productId = Integer.parseInt(requestData.get("productId").toString());
        int quantity = Integer.parseInt(requestData.get("quantity").toString());
        String cartKey = "cart:user:" + userId;
        if (redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(productId))) {
            int currentqutity = (int) redisTemplate.opsForHash().get(cartKey, String.valueOf(productId));
            redisTemplate.opsForHash().put(cartKey, String.valueOf(productId), currentqutity + quantity);
        } else {
            redisTemplate.opsForHash().put(cartKey, String.valueOf(productId), quantity);
        }
        return CommonResult.success();
    }

    // 更新购物车中商品的数量
    @PostMapping("/updateCartItem")
    public CommonResult updateCartItem(@RequestBody Map<String, Object> requestData) {
        // 将参数先转换为字符串，再手动解析
        int userId = Integer.parseInt(requestData.get("userId").toString());
        int productId = Integer.parseInt(requestData.get("productId").toString());
        int quantity = Integer.parseInt(requestData.get("quantity").toString());
        String cartKey = "cart:user:" + userId;
        if (quantity > 0) {
            redisTemplate.opsForHash().put(cartKey, String.valueOf(productId), quantity);
        } else {
            redisTemplate.opsForHash().delete(cartKey, String.valueOf(productId));
        }
        return CommonResult.success();
    }

    // 删除购物车中的商品
    @PostMapping("/removeFromCart")
    public CommonResult removeFromCart(@RequestBody Map<String, Object> requestData) {
        int userId = Integer.parseInt(requestData.get("userId").toString());
        int productId = Integer.parseInt(requestData.get("productId").toString());
        String cartKey = "cart:user:" + userId;
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(productId));
        return CommonResult.success();
    }

    // 清空购物车
    @PostMapping("/clearCart")
    public CommonResult clearCart(@RequestBody Map<String, Object> requestData) {
        int userId = Integer.parseInt(requestData.get("userId").toString());
        String cartKey = "cart:user:" + userId;
        redisTemplate.delete(cartKey);
        return CommonResult.success();
    }

    // 查看购物车中的所有商品
    @GetMapping("/getCartItems")
    public CommonResult getCartItems(@RequestParam("userId") int userId) {
        List<CartItem> list = cartService.getCartItems(userId);
        return CommonResult.success(list);
    }



}
