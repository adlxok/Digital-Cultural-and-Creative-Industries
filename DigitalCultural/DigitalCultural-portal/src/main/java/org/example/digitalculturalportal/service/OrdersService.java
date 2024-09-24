package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.Orders;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/24 8:17
 **/
public interface OrdersService {

    List<Orders> selectAll(int userId);

    void updateOrderStatus(int orderId, int status);

    Orders selectOrderById(int orderId);

    void updateOrders(Orders orders);

    int addOrders(List<Orders> list);

}
