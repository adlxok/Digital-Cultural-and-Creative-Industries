package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author adlx
 * @since 2024-07-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigationItems implements Serializable {

    /**
     * 导航项唯一标识，自增
     */

    private Integer id;

    /**
     * 导航项名称，例如"首页"或"论坛"
     */
    private String name;

    /**
     * 导航项链接地址，指向该项的目标页面
     */
    private String url;

    /**
     * 父导航项ID，用于表示子导航项。NULL表示顶级导航项
     */
    private Integer parentId;

    /**
     * 导航项显示顺序，数值越小越靠前
     */
    private Integer position;

    /**
     * 导航项是否激活，TRUE表示显示，FALSE表示隐藏
     */
    private Boolean isActive;

    /**
     * 导航项创建时间
     */
    private Date createdAt;

    /**
     * 导航项最后更新时间
     */
    private Date updatedAt;


}
