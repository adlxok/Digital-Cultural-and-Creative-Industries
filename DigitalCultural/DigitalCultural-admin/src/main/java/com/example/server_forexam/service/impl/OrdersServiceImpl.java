package com.example.server_forexam.service.impl;


import com.example.server_forexam.dao.OrdersMapper;
import com.example.server_forexam.entity.Orders;
import com.example.server_forexam.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户订单表 服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-09-24
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public List<Orders> selectAllOrders() {
        return ordersMapper.selectAllOrders();
    }

    @Override
    public void deleteOrderById(int orderId) {
        ordersMapper.deleteOrderById(orderId);
    }

    @Override
    public void OrderDelivery(Orders orders) {
        ordersMapper.OrderDelivery(orders);
    }
}
