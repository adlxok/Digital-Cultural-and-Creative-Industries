package org.example.digitalculturalportal.controller;

import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.OrderRequest;
import org.example.digitalculturalportal.pojo.Orders;
import org.example.digitalculturalportal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/24 8:18
 **/

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/selectAllOrders")
    public CommonResult seleceAllOrders(@RequestParam int userId) {
        List<Orders> list = ordersService.selectAll(userId);
        return CommonResult.success(list);
    }

    @PostMapping("/updateOrderStatus")
    public CommonResult updateOrderStatus(@RequestBody Map<String, Object> reqData) {
        int orderId = Integer.parseInt(reqData.get("orderId").toString());
        int status = Integer.parseInt(reqData.get("status").toString());
        ordersService.updateOrderStatus(orderId, status);
        return CommonResult.success();
    }

    @GetMapping("/selectOrderById")
    public CommonResult selectOrderById(@RequestParam int orderId) {
        Orders orders = ordersService.selectOrderById(orderId);
        return CommonResult.success(orders);
    }

    @PostMapping("/updateOrders")
    public CommonResult updateOrders(@RequestBody Orders orders) {
        ordersService.updateOrders(orders);
        return CommonResult.success();
    }

    @PostMapping("/addOrders")
    public CommonResult addOrders(@RequestBody OrderRequest orderRequest) {
        int num = ordersService.addOrders(orderRequest.getOrdersList());
        String cartKey = "cart:user:" + orderRequest.getUserId();
        List<Integer> proIdList = orderRequest.getProIdList();
        for (int proId : proIdList) {
            redisTemplate.opsForHash().delete(cartKey, String.valueOf(proId));
        }

        return CommonResult.success(num);
    }



}
