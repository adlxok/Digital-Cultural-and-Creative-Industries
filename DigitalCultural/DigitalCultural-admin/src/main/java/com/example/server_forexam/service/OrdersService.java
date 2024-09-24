package com.example.server_forexam.service;


import com.example.server_forexam.entity.Orders;

import java.util.List;

/**
 * <p>
 * 用户订单表 服务类
 * </p>
 *
 * @author adlx
 * @since 2024-09-24
 */
public interface OrdersService  {

    List<Orders> selectAllOrders();

    void deleteOrderById(int orderId);

    void OrderDelivery(Orders orders);

}
