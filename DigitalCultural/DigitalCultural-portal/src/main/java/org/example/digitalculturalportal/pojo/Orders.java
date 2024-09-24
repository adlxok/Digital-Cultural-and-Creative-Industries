package org.example.digitalculturalportal.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户订单表
 * </p>
 *
 * @author adlx
 * @since 2024-09-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单状态：1=待发货，2=运输中，3=待收货，4=已收货
     */
    private Integer status;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderDate;

    /**
     * 逻辑删除字段：0 未删除 1 已删除
     */
    private Boolean isDeleted;

    /**
     * 收货人姓名
     */
    private String recipientName;

    /**
     * 收货人电话
     */
    private String recipientPhone;

    /**
     * 收货地址
     */
    private String shippingAddress;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单编号
     */
    private String orderSn;


}
