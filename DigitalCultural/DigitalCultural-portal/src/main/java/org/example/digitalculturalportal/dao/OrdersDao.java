package org.example.digitalculturalportal.dao;


import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.Orders;

import java.util.List;

/**
 * <p>
 * 用户订单表 Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-09-24
 */
public interface OrdersDao {

    List<Orders> selectAll(@Param("userId") int userId);

    void updateOrderStatus(@Param("orderId") int orderId, @Param("status") int status);

    Orders selectOrderById(@Param("orderId") int orderId);

    void updateOrders(Orders orders);

    int addOrders(@Param("list") List<Orders> ordersList);

}
