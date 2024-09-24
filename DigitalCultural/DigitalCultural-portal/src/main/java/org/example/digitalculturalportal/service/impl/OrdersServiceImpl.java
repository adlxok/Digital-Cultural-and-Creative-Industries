package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.OrdersDao;
import org.example.digitalculturalportal.pojo.Orders;
import org.example.digitalculturalportal.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/24 8:17
 **/

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public List<Orders> selectAll(int userId) {
        return ordersDao.selectAll(userId);
    }

    @Override
    public void updateOrderStatus(int orderId, int status) {
        ordersDao.updateOrderStatus(orderId, status);
    }

    @Override
    public Orders selectOrderById(int orderId) {
        return ordersDao.selectOrderById(orderId);
    }

    @Override
    public void updateOrders(Orders orders) {
        ordersDao.updateOrders(orders);
    }

    @Override
    public int addOrders(List<Orders> list) {
        return ordersDao.addOrders(list);
    }
}
