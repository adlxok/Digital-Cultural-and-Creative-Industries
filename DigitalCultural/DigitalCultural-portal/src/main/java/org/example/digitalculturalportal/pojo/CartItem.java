package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/12 20:33
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String image;
    private Boolean selected;

}
