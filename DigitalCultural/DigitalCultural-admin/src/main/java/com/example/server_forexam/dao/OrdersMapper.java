package com.example.server_forexam.dao;


import com.example.server_forexam.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户订单表 Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-09-24
 */
public interface OrdersMapper {

    List<Orders> selectAllOrders();

    void deleteOrderById(@Param("orderId") int orderId);

    void OrderDelivery(Orders orders);

}
