package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/24 17:04
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<Orders> ordersList;
    private List<Integer> proIdList;
    private Integer userId;

}
