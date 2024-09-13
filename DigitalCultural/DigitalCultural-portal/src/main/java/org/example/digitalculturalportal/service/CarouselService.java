package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.entity.Carousel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Carousel)表服务接口
 *
 * @author adlx
 * @since 2024-09-05 15:15:18
 */
public interface CarouselService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Carousel queryById(Integer id);

    /**
     * 分页查询
     *
     * @param carousel 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Carousel> queryByPage(Carousel carousel, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param carousel 实例对象
     * @return 实例对象
     */
    Carousel insert(Carousel carousel);

    /**
     * 修改数据
     *
     * @param carousel 实例对象
     * @return 实例对象
     */
    Carousel update(Carousel carousel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
